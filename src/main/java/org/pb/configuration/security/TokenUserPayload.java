package org.pb.configuration.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.pb.model.user.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class TokenUserPayload {
    private String login;
    private List<String> roles;

    public static TokenUserPayload of(User user) {

        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(toList());

        return new TokenUserPayload(user.getUsername(), roles);
    }

    public static TokenUserPayload of(String login, Set<Role> rolesSet) {
        List<String> roleNames = rolesSet.stream()
                .map(Role::getRoleName)
                .collect(toList());
        return new TokenUserPayload(login, roleNames);
    }
}
