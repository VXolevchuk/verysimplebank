package com.example.verysimplebank.repos;

import com.example.verysimplebank.dto.TransactionDTO;
import com.example.verysimplebank.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t join fetch t.sender WHERE t.sender.login = :login")
    List<Transaction> getTransactionDTO(@Param("login") String login);

}
