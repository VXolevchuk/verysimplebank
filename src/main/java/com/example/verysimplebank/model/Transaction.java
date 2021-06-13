package com.example.verysimplebank.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Transactions")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString(exclude = {"account"})
public  class Transaction {
    @Id
    @GeneratedValue
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private double value;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    private Long relatedAccountNumber;

    public Transaction(Date date, Currency currency, TransactionType type, double value) {
        this.date = date;
        this.currency = currency;
        this.type = type;
        this.value = value;
    }

}
