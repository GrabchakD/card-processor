package org.pb.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.pb.model.user.User;
import org.pb.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static org.pb.configuration.security.SecurityConstants.*;
import static org.pb.utils.BaseUtils.doTry;

public class AuthorizationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public AuthorizationFilter(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        setFilterProcessesUrl("/api/v1/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            User creds = new ObjectMapper().readValue(req.getInputStream(), User.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getLogin(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new BadCredentialsException(e.getMessage());
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) {

        addTokenToResponse(res, (UserDetails) auth.getPrincipal());
        addOperatorNameToResponse(res, auth.getName());
    }

    public static void addTokenToResponse(HttpServletResponse res, UserDetails userDetails) {
        ObjectMapper mapper = new ObjectMapper();
        TokenUserPayload payload = TokenUserPayload.of((org.springframework.security.core.userdetails.User) userDetails);

        String token = Jwts.builder()
                .setSubject(doTry(() -> mapper.writeValueAsString(payload)))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET.getBytes())
                .compact();
        res.addHeader(HEADER_STRING, token);
        res.addHeader(ALLOW_TOKEN_ACCESS_HEADER, HEADER_STRING);
    }

    private void addOperatorNameToResponse(HttpServletResponse res, String login) {
        User user = userService.findByLogin(login);
        res.addHeader(OPERATOR_NAME_STRING, user.getFullName());
    }
}
