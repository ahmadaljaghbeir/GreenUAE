package org.example.greenuae.service.impl;

import jakarta.mail.MessagingException;

import org.example.greenuae.exception.ResourceNotFoundException;
import org.example.greenuae.model.AuthResponse;
import org.example.greenuae.model.UserEntity;
import org.example.greenuae.repository.UserRepository;
import org.example.greenuae.security.JwtGenerator;
import org.example.greenuae.service.LoginService;
import org.example.greenuae.util.EmailUtil;
import org.example.greenuae.util.OtpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class LoginServiceImpl implements LoginService {
    private AuthenticationManager authenticationManager;
    private JwtGenerator jwtGenerator;
    private OtpUtil otpUtil;
    private EmailUtil emailUtil;
    private UserRepository userRepository;

    @Autowired
    public LoginServiceImpl(AuthenticationManager authenticationManager, JwtGenerator jwtGenerator, OtpUtil otpUtil,
                            EmailUtil emailUtil, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
        this.otpUtil = otpUtil;
        this.emailUtil = emailUtil;
        this.userRepository = userRepository;
    }

    @Override
    public String auth(UserEntity userEntity) {
        Authentication authentication = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(userEntity.getEmail()
                        , userEntity.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "true";
    }

    @Override
    public String twoFactorAuth(UserEntity user) {
        String otp = otpUtil.generateOtp();
        try {
            emailUtil.sendOtpEmail(user.getEmail(), otp);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send otp please try again");
        }
        UserEntity userEntity = userRepository.findByEmail(user.getEmail()).orElseThrow(
                () -> new ResourceNotFoundException("User", "Email", user.getEmail()));
        userEntity.setOtp(otp);
        userEntity.setOtpGeneratedTime(LocalDateTime.now());
        userRepository.save(userEntity);

        return "successful";
    }

    @Override
    public String verifyAccount(String email, String otp) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
        if (user.getOtp().equals(otp) && Duration.between(user.getOtpGeneratedTime(),
                LocalDateTime.now()).getSeconds() < (1 * 60)) {
            user.setActive(true);
            userRepository.save(user);
            return "new AuthResponse(token);";
        }
        return "null";
    }

    @Override
    public String regenerateOtp(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
        String otp = otpUtil.generateOtp();
        try {
            emailUtil.sendOtpEmail(email, otp);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send otp please try again");
        }
        user.setOtp(otp);
        user.setOtpGeneratedTime(LocalDateTime.now());
        userRepository.save(user);
        return "Email sent... please verify account within 1 minute";
    }

    @Override
    public AuthResponse verifyOtp(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
        boolean isActive = user.isActive();
        if (isActive){
            String token = jwtGenerator.generateToken(email);
            return new AuthResponse(token);
        }
        return null;
    }
}
