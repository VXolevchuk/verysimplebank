package com.example.verysimplebank.dto;

import com.example.verysimplebank.model.Currency;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransactionToNotifyDTO {
    private String date;
    private double value;
    private Long senderCardNumber;
    private Currency currency;

    public TransactionToNotifyDTO(String date, double value, Long senderCardNumber, Currency currency) {
        this.date = date;
        this.value = value;
        this.senderCardNumber = senderCardNumber;
        this.currency = currency;
    }
}
