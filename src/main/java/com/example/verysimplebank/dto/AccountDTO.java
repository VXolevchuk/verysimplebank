package com.example.verysimplebank.dto;

import com.example.verysimplebank.model.Currency;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data @NoArgsConstructor
public class AccountDTO {
    private Long accountNumber;
    private double amount;
    private String currency;

    public AccountDTO(Long accountNumber, double amount, String currency) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.currency = currency;
    }

    public AccountDTO(Long accountNumber, String currency) {
        this.accountNumber = accountNumber;
        this.currency = currency;
    }
}
