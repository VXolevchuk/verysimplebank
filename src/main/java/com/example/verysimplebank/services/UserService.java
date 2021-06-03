package com.example.verysimplebank.services;

import com.example.verysimplebank.model.CustomUser;
import com.example.verysimplebank.model.UserRole;

public interface UserService {
    void addUser(CustomUser customUser);
    CustomUser findByLogin(String login);
    boolean addUser(String login, String passHash,
                    UserRole role, boolean isBlocked);
}
