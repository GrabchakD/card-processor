package org.pb.configuration.security;

import lombok.RequiredArgsConstructor;
import org.pb.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserService userService;
    private final BCryptPasswordEncoder encoder;
    private final String ADMIN_ROLE = "ADMIN";
    private final String USER_ROLE = "USER";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/v1/clients/**").hasRole(USER_ROLE)
                .antMatchers("/api/v1/orders/generate-card-numbers").hasRole(ADMIN_ROLE)
                .antMatchers("/api/v1/orders/**").hasAnyRole(ADMIN_ROLE, USER_ROLE)
                .antMatchers("/api/v1/cards/types").hasAnyRole(ADMIN_ROLE, USER_ROLE)
                .antMatchers("/api/v1/cards/**").hasRole(ADMIN_ROLE)
                .antMatchers("/api/v1/statistic/**").hasRole(ADMIN_ROLE)
                .anyRequest().authenticated()
                .and()
                .addFilter(authorizationFilter(userService))
                .addFilter(authenticationFilter())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    private AuthorizationFilter authorizationFilter(UserService userService) throws Exception {
        return new AuthorizationFilter(authenticationManager(), userService);
    }

    private AuthenticationFilter authenticationFilter() throws Exception {
        return new AuthenticationFilter(authenticationManager());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration conf = new CorsConfiguration().applyPermitDefaultValues();
        conf.addAllowedOrigin("http://localhost:4200");
        conf.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        conf.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type", "Operator-Name"));
        conf.setAllowCredentials(true);
        conf.setExposedHeaders(Arrays.asList("Authorization", "Referer", "Origin", "Operator-Name"));
        source.registerCorsConfiguration("/**", conf);
        return source;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(encoder);
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(Collections.singletonList(authenticationProvider));
    }
}
