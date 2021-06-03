package com.example.verysimplebank.services;

import com.example.verysimplebank.dto.TransactionDTO;
import com.example.verysimplebank.dto.TransactionToShowDTO;

import java.util.List;

public interface TransactionService {
    void performTransaction(Long performerNumber, Long receiverNumber, double value);
    List<TransactionToShowDTO> getAllByUser(String login);
}
