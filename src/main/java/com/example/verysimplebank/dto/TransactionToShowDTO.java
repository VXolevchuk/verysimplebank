package com.example.verysimplebank.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransactionToShowDTO {
    private String date;
    private double value;
    private Long receiverCardNumber;
    private  String senderLogin;
    private String currency;

    public TransactionToShowDTO(String date, double value, Long receiverCardNumber, String senderLogin, String currency) {
        this.date = date;
        this.value = value;
        this.receiverCardNumber = receiverCardNumber;
        this.senderLogin = senderLogin;
        this.currency = currency;
    }
}
