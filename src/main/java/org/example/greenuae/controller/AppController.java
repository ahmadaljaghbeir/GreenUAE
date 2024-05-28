package org.example.greenuae.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AppController {

    @GetMapping( "/")
    public String getPage() {
        return "home";
    }

    @GetMapping(value = "/signUp")
    public String signUp() {
        return "signUp";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping(value = "/twoFactorAuth")
    public String twoFactorAuth(@RequestParam(name = "email") String email, Model model) {
        model.addAttribute("email", email);
        return "twoFactorAuth";
    }

    @GetMapping(value = "/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping(value = "/uploadPage")
    public String uploadPhoto() {
        return "uploadPage";
    }

    @GetMapping(value = "/history")
    public String history() {
        return "history";
    }

    @GetMapping(value = "/points")
    public String points() {
        return "points";
    }

    @GetMapping(value = "/treeInfoPage")
    public String treeInfo() {
        return "treeInfo";
    }
}
