package org.example.greenuae.service;

import org.example.greenuae.model.UserEntity;


public interface SignUpService {
    UserEntity addUser(UserEntity userEntity);

    UserEntity getUserById(long id);

    UserEntity updateUser(UserEntity userEntity, long id);

    void deleteUser(long id);
}
