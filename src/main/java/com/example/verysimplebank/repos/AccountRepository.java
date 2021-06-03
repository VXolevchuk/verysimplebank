package com.example.verysimplebank.repos;

import com.example.verysimplebank.model.Account;
import com.example.verysimplebank.model.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT a FROM Account a WHERE a.customUser.login = :login")
    List<Account> findAccountsByCustomUser_Login(@Param("login") String login);
    Account findAccountByAccountNumber(Long number);
    Long countAccountsByAccountNumberIsNotNull();
}
