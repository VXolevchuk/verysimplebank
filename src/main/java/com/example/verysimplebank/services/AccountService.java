package com.example.verysimplebank.services;

import com.example.verysimplebank.dto.AccountDTO;
import com.example.verysimplebank.model.Account;

import java.util.List;

public interface AccountService {
    void addAccount(Account account);

    List<AccountDTO> getAccountsByUser(String userLogin);

    Account getAccountByNumber(Long accountNumber);
    Long countAccounts();
}
