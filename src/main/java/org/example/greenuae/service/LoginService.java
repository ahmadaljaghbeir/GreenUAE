package org.example.greenuae.service;

import org.example.greenuae.model.AuthResponse;
import org.example.greenuae.model.UserEntity;

public interface LoginService {
    AuthResponse auth(UserEntity user);
}
