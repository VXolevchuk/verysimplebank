package com.example.verysimplebank.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Accounts")
@Data @NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue
    private Long id;

    private Long accountNumber;

    private double money;
    @Enumerated(EnumType.STRING)
    private Currency currency;

    private String dateOfCreation;
    private boolean isBlocked;

    @ManyToOne
    @JoinColumn(name = "customuser_id")
    private CustomUser customUser;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "recipient")
    private List<Transaction> receivedTransactions;


    public Account(Long accountNumber, double money, String dateOfCreation, boolean isBlocked) {
        this.accountNumber = accountNumber;
        this.money = money;
        this.dateOfCreation = dateOfCreation;
        this.isBlocked = isBlocked;
    }

    public Account(Long accountNumber, double money, Currency currency) {
        this.accountNumber = accountNumber;
        this.money = money;
        this.currency = currency;
    }

    public void setReceivedTransaction(Transaction transaction) {
        transaction.setRecipient(this);
        this.receivedTransactions.add(transaction);
    }

    public void addMoney(double money) {
        this.money += money;
    }
}
