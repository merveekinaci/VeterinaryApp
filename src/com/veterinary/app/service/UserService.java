package com.veterinary.app.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.veterinary.app.dto.UserUpdateDto;
import com.veterinary.app.model.User;
import com.veterinary.app.dto.UserRegistrationDto;

import java.util.List;
import java.util.Optional;

public interface UserService  extends UserDetailsService {
    User findByEmail(String email);

    Optional <User> findById(Long id);

    void save(UserRegistrationDto registration);

    void update(UserUpdateDto userUpdate, User user);

    List <User> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);
}