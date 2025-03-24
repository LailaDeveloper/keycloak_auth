package com.demo.demo_keycloak.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {

    @GetMapping("/")
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
}
