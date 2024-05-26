package org.example.greenuae.service.impl;

import org.example.greenuae.model.UserEntity;
import org.example.greenuae.repository.UserRepository;
import org.example.greenuae.service.LogoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogoutServiceImpl implements LogoutService {
    private UserRepository userRepository;

    @Autowired
    public LogoutServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String logout(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
        user.setActive(false);
        user.setOtp(null);
        user.setOtpGeneratedTime(null);
        userRepository.save(user);
        System.out.println("user.toString()");
        return "Logged out successfully";
    }
}
