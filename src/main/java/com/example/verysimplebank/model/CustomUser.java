package com.example.verysimplebank.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CustomUsers")
@Data @NoArgsConstructor
public class CustomUser {
    @Id
    @GeneratedValue
    private Long id;

    private String login;
    private String password;
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private boolean isBlocked;

    @OneToMany(mappedBy = "customUser", cascade = CascadeType.ALL)
    private List<Account> accounts = new ArrayList<Account>();

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private List<Transaction> transactions = new ArrayList<Transaction>();

    public CustomUser(String login, String password, String email, UserRole role, boolean isBlocked) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.role = role;
        this.isBlocked = isBlocked;
    }

    public CustomUser(String login, String password, UserRole role, boolean isBlocked) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.isBlocked = isBlocked;
    }

    public void addAccount(Account account) {
        account.setCustomUser(this);
        this.accounts.add(account);
    }

    public void addTransaction(Transaction transaction) {
        transaction.setSender(this);
        this.transactions.add(transaction);
    }
}
