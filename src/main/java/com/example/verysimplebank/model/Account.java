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

    private double money;
    @Enumerated(EnumType.STRING)
    private Currency currency;

    private String dateOfCreation;
    private boolean isBlocked;

    @ManyToOne
    @JoinColumn(name = "customuser_id")
    private CustomUser customUser;

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, mappedBy = "account")
    private List<Transaction> transactions;


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

    public void setTransaction(Transaction transaction) {
        transaction.setAccount(this);
        this.transactions.add(transaction);
    }

    public void addMoney(double money) {
        this.money += money;
    }
}
