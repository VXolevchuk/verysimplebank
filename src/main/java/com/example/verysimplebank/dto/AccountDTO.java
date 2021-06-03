package com.example.verysimplebank.dto;

import com.example.verysimplebank.model.Currency;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data @NoArgsConstructor
public class AccountDTO {
    private Long accountNumber;
    private double money;
    private String currency;

    public AccountDTO(Long accountNumber, double money, String currency) {
        this.accountNumber = accountNumber;
        this.money = money;
        this.currency = currency;
    }
}
