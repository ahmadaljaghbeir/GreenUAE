package org.example.greenuae.service;

import org.example.greenuae.model.UserEntity;


public interface SignUpService {
    UserEntity addUser(UserEntity userEntity);

    UserEntity getUserByEmail(String email);

    UserEntity updateUser(UserEntity userEntity, long id);

    void deleteUser(long id);
}
