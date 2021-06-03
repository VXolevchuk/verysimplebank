package com.example.verysimplebank.dto;

import com.example.verysimplebank.model.Currency;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data @NoArgsConstructor
public class TransactionDTO {
    private String date;
    private double value;
    private Long senderCardNumber;
    private Long receiverCardNumber;

    private  String senderLogin;

    private String currency;


    public TransactionDTO(String date, double value, Long senderCardNumber, Long receiverCardNumber) {
        this.date = date;
        this.value = value;
        this.senderCardNumber = senderCardNumber;
        this.receiverCardNumber = receiverCardNumber;
    }

    @JsonCreator
    public TransactionDTO(@JsonProperty(required = true) Long sender,
                    @JsonProperty(required = true) Long receiver,
                    @JsonProperty(required = true) double value) {
        this.senderCardNumber = sender;
        this.receiverCardNumber = receiver;
        this.value = value;
    }

    public TransactionDTO(double value, Long senderCardNumber, Long receiverCardNumber) {
        this.value = value;
        this.senderCardNumber = senderCardNumber;
        this.receiverCardNumber = receiverCardNumber;
    }

    public TransactionDTO(String date, double value, String senderLogin, Long receiverCardNumber, String currency) {
        this.date = date;
        this.value = value;
        this.senderLogin = senderLogin;
        this.receiverCardNumber = receiverCardNumber;
        this.currency = currency;
    }


}
