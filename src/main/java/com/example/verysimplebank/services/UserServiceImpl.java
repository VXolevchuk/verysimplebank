package com.example.verysimplebank.services;

import com.example.verysimplebank.model.CustomUser;
import com.example.verysimplebank.model.UserRole;
import com.example.verysimplebank.repos.CustomUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService{
    private final CustomUserRepository userRepository;

    public UserServiceImpl(CustomUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public void addUser(CustomUser customUser) {
        userRepository.save(customUser);
    }

    @Transactional
    @Override
    public boolean addUser(String login, String passHash,
                           UserRole role, boolean isBlocked) {
        if (userRepository.existsByLogin(login))
            return false;

        CustomUser user = new CustomUser(login, passHash, role, isBlocked);
        userRepository.save(user);

        return true;
    }

    @Transactional(readOnly = true)
    @Override
    public CustomUser findByLogin(String login) {
        return userRepository.findCustomUserByLogin(login);
    }
}
