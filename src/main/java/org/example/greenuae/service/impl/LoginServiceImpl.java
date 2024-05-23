package org.example.greenuae.service.impl;

import org.example.greenuae.Security.JwtGenerator;
import org.example.greenuae.model.AuthResponse;
import org.example.greenuae.model.UserEntity;
import org.example.greenuae.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    private AuthenticationManager authenticationManager;
    private JwtGenerator jwtGenerator;

    @Autowired
    public LoginServiceImpl(AuthenticationManager authenticationManager, JwtGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
    }

    @Override
    public AuthResponse auth(UserEntity userEntity) {
        Authentication authentication = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(userEntity.getEmail()
                        , userEntity.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return new AuthResponse(token);
    }
}
