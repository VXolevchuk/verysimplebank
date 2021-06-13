package com.example.verysimplebank.dto;

import com.example.verysimplebank.model.Currency;
import com.example.verysimplebank.model.TransactionType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransactionToDisplayDTO {
    private String date1;
    private double value1;
    private Long senderCardNumber;
    private Long receiverCardNumber;
    private Currency currency;
    private TransactionType type1;

    public TransactionToDisplayDTO(String date, double value, Long senderCardNumber, Long receiverCardNumber, Currency currency, TransactionType type) {
        this.date1 = date;
        this.value1 = value;
        this.senderCardNumber = senderCardNumber;
        this.receiverCardNumber = receiverCardNumber;
        this.currency = currency;
        this.type1 = type;
    }
}
