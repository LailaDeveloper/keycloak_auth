package com.demo.demo_keycloak.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class RedirectController {

    @Value("${keycloak.logout-url}")
    String KEYCLOAK_LOGOUT_URL;

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal OidcUser user, Model model) {
        if (user != null) {
            model.addAttribute("username", user.getPreferredUsername());
            model.addAttribute("roles", user.getAuthorities());
        }
        return "home"; // Affiche la page home.html
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin"; // Affiche la page admin.html
    }

    @GetMapping("/client")
    public String client(@AuthenticationPrincipal OidcUser user, Model model) {
        model.addAttribute("name", user.getPreferredUsername());
        return "client"; // Affiche la page client.html
    }

    @GetMapping("/logout")
    public void directLogout(HttpServletRequest request,HttpServletResponse response) throws IOException {

        // 1. Invalider la session Spring
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // 2. Nettoyer le contexte de sécurité
        SecurityContextHolder.clearContext();
        String logoutUrl = "http://localhost:8080/realms/intelcom_platform/protocol/openid-connect/logout" +
                "?redirect_uri=http://localhost:8080/login";
        response.sendRedirect(logoutUrl);
    }
}
