package com.demo.demo_keycloak.Configuration;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        // Récupère les rôles du token JWT
        List<String> roles = (List<String>) jwt.getClaims().get("roles");
        if (roles == null || roles.isEmpty()) {
            return Collections.emptyList();
        }

        // Mappe les rôles en autorités Spring Security
        return roles.stream()
                .map(role -> {
                    // Transforme "Role_mapperAdmin" en "ROLE_ADMIN"
                    if (role.equals("Role_mapperAdmin")) {
                        return new SimpleGrantedAuthority("ROLE_ADMIN");
                    }
                    // Transforme "Role_mapperClient" en "ROLE_CLIENT"
                    if (role.equals("Role_mapperClient")) {
                        return new SimpleGrantedAuthority("ROLE_CLIENT");
                    }
                    return new SimpleGrantedAuthority("ROLE_" + role.toUpperCase());
                })
                .collect(Collectors.toList());
    }
}

