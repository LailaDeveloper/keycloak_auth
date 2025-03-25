package com.demo.demo_keycloak.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.web.SecurityFilterChain;

import java.util.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${keycloak.logout-url}")
    String KEYCLOAK_LOGOUT_URL;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/admin").hasRole("ADMIN") // Accès réservé aux admins
                .requestMatchers("/client").hasRole("CLIENT") // Accès réservé aux clients
                .anyRequest().authenticated() // Toutes les autres requêtes nécessitent une authentification
            )
            .oauth2Login(oauth2 -> oauth2
                .defaultSuccessUrl("/home", true) // Redirection après une authentification réussie
                .userInfoEndpoint(userInfo -> userInfo
                    .userAuthoritiesMapper(this.userAuthoritiesMapper()) // Utilise le mapper personnalisé
                )
            )
                .logout(logout -> logout
                        .disable() // Désactive complètement le logout de Spring Security
                );

        return http.build();
    }

    @Bean
    public GrantedAuthoritiesMapper userAuthoritiesMapper() {
        return (authorities) -> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

            authorities.forEach(authority -> {
                if (authority instanceof OidcUserAuthority) {
                    OidcUserAuthority oidcUserAuthority = (OidcUserAuthority) authority;

                    // Récupère les rôles du token JWT
                    Map<String, Object> claims = oidcUserAuthority.getIdToken().getClaims();


                    List<String> roles = (List<String>) claims.get("roles");

                    if (roles != null) {
                        roles.forEach(role -> {
                            // Transforme "Role_mapperAdmin" en "ROLE_ADMIN"
                            if (role.equals("Role_mapperAdmin")) {
                                mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                            }
                            // Transforme "Role_mapperClient" en "ROLE_CLIENT"
                            if (role.equals("Role_mapperClient")) {
                                mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_CLIENT"));
                            }
                        });
                    }
                }
            });

            return mappedAuthorities;
        };
    }
}