package org.example.greenuae.service;

import org.example.greenuae.model.AuthResponse;
import org.example.greenuae.model.UserEntity;

public interface LoginService {
    String auth(UserEntity user);
    String twoFactorAuth(UserEntity user);
    String verifyAccount(String email, String otp);
    AuthResponse verifyOtp(String email);
}
