package com.example.verysimplebank.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Transactions")
@Data @NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue
    private Long id;

    private String date;

    @ManyToOne
    @JoinColumn(name="customuser_id")
    private Account sender;

    @ManyToOne
    @JoinColumn(name="account_id")
    private Account recipient;

    private String currency;

    private double value;

    public Transaction(String date, double value, String currency) {
        this.date = date;
        this.value = value;
        this.currency = currency;
    }




}
