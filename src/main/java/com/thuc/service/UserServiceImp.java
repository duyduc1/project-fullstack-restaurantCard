package com.thuc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thuc.config.JwtProvider;
import com.thuc.model.User;
import com.thuc.repository.UserRepository;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        User user = userRepository.findByEmail(email);

        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new Exception("User not found with email " + email);
        }

        return user;
    }

}
