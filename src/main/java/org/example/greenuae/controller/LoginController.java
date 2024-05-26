package org.example.greenuae.controller;

import org.example.greenuae.model.AuthResponse;
import org.example.greenuae.model.UserEntity;
import org.example.greenuae.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    private LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        super();
        this.loginService = loginService;
    }

    @PostMapping(value = "/auth")
    public ResponseEntity<String> login(@RequestBody UserEntity userEntity) {
        return new ResponseEntity<String>(loginService.auth(userEntity), HttpStatus.OK);
    }

    @PostMapping(value = "/mfa")
    public ResponseEntity<String> twoFactorAuth(@RequestBody UserEntity userEntity) {
        return new ResponseEntity<String>(loginService.twoFactorAuth(userEntity), HttpStatus.OK);
    }

    @PutMapping(value = "/verify-account")
    public ResponseEntity<String> verifyAccount(@RequestParam String email, @RequestParam String otp) {
        return new ResponseEntity<>(loginService.verifyAccount(email, otp), HttpStatus.OK);
    }

    @GetMapping(value = "/verify")
    public ResponseEntity<AuthResponse> verifyAccount(@RequestParam String email) {
        return new ResponseEntity<AuthResponse>(loginService.verifyOtp(email), HttpStatus.OK);
    }

    @PutMapping("/regenerate-otp")
    public ResponseEntity<String> regenerateOtp(@RequestParam String email) {
        return new ResponseEntity<>(loginService.regenerateOtp(email), HttpStatus.OK);
    }
}