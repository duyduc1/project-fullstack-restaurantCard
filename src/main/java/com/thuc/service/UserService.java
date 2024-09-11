package com.thuc.service;

import com.thuc.model.User;

public interface UserService {

    public User findUserByJwtToken(String jwt) throws Exception;

    public User findUserByEmail(String email) throws Exception;

}
