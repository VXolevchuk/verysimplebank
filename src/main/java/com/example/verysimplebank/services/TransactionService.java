package com.example.verysimplebank.services;

import com.example.verysimplebank.dto.TransactionToDisplayDTO;

import java.util.List;

public interface TransactionService {
    void performTransaction(Long performerNumber, Long receiverNumber, double value);
    List<TransactionToDisplayDTO> getAllByUser(String login);
}
