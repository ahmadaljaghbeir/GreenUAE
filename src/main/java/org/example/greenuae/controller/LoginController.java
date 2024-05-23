package org.example.greenuae.controller;

import org.example.greenuae.model.AuthResponse;
import org.example.greenuae.model.UserEntity;
import org.example.greenuae.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        super();
        this.loginService = loginService;
    }

    @PostMapping(value = "/auth")
    public ResponseEntity<AuthResponse> login(@RequestBody UserEntity userEntity) {
        return new ResponseEntity<AuthResponse>(loginService.auth(userEntity), HttpStatus.OK);
    }
}