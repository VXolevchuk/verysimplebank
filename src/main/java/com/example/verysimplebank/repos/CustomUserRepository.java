package com.example.verysimplebank.repos;

import com.example.verysimplebank.model.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomUserRepository extends JpaRepository<CustomUser, Long> {
    CustomUser findCustomUserByLogin(String login);
    boolean existsByLogin(String login);
}
