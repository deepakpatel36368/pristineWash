package com.website.pristinewash.service;

import com.website.pristinewash.entity.User;

public interface UserService {

    public User getUserById(Integer userId);

    public String addUser(User user);
}
