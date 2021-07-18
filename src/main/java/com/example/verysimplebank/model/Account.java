package com.example.verysimplebank.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Accounts")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString(exclude = {"customUser", "transactions"})
public class Account {
    @Id
    @GeneratedValue
    private Long id;

    private Long accountNumber;

    private double amount;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private String dateOfCreation;
    private boolean isBlocked;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "customuser_id")
    private CustomUser customUser;

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, mappedBy = "account")
    private List<Transaction> transactions;


    public Account(Long accountNumber, double amount, String dateOfCreation, boolean isBlocked) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.dateOfCreation = dateOfCreation;
        this.isBlocked = isBlocked;
    }

    public Account(Long accountNumber, double amount, Currency currency) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.currency = currency;
    }

    public Account(Long accountNumber, Currency currency) {
        this.accountNumber = accountNumber;
        this.currency = currency;
    }

    public void setTransaction(Transaction transaction) {
        transaction.setAccount(this);
        this.transactions.add(transaction);
    }

}
