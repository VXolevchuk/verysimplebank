package com.example.verysimplebank.dto;

import com.example.verysimplebank.model.Currency;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data @NoArgsConstructor
public class TransactionDTO {
    private double value;
    private Long senderCardNumber;
    private Long receiverCardNumber;



    @JsonCreator
    public TransactionDTO(@JsonProperty(required = true) Long sender,
                    @JsonProperty(required = true) Long receiver,
                    @JsonProperty(required = true) double value) {
        this.senderCardNumber = sender;
        this.receiverCardNumber = receiver;
        this.value = value;
    }

}
