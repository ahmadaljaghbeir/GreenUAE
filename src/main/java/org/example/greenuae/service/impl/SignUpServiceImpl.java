package org.example.greenuae.service.impl;

import org.example.greenuae.exception.ResourceNotFoundException;
import org.example.greenuae.model.Role;
import org.example.greenuae.model.UserEntity;
import org.example.greenuae.repository.RoleRepository;
import org.example.greenuae.repository.UserRepository;
import org.example.greenuae.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class SignUpServiceImpl implements SignUpService {
//    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public SignUpServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
//        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserEntity addUser(UserEntity userEntity) {
        if (userRepository.existsByEmail(userEntity.getEmail()))
            return null;

        String password = userEntity.getPassword();
        userEntity.setPassword(passwordEncoder.encode(password));
        Role role = roleRepository.findByRole("USER").get();
        userEntity.setRoles(Collections.singletonList(role));

        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("User", "Email", email));
    }

    @Override
    public UserEntity updateUser(UserEntity userEntity, String email) {
        UserEntity existingUserEntity = userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("User", "Id", email));

        existingUserEntity.setFirst_name(userEntity.getFirst_name());
        existingUserEntity.setLast_name(userEntity.getLast_name());
        existingUserEntity.setEmail(userEntity.getEmail());
        existingUserEntity.setDate_of_birth(userEntity.getDate_of_birth());
        existingUserEntity.setPhone_number(userEntity.getPhone_number());


        userRepository.save(existingUserEntity);
        return existingUserEntity;
    }

    @Override
    public void deleteUser(long id) {
        userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "Id", id));
        userRepository.deleteById(id);
    }
}