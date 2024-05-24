package org.example.greenuae.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping(value = "/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping(value = "/uploadPage")
    public String uploadPhoto() {
        return "uploadPage";
    }
}
