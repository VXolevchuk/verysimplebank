package com.example.verysimplebank.services;

import com.example.verysimplebank.dto.AccountDTO;
import com.example.verysimplebank.model.Account;
import com.example.verysimplebank.repos.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    @Override
    public void addAccount(Account account) {
        accountRepository.save(account);
    }

    @Transactional(readOnly = true)
    @Override
    public List<AccountDTO> getAccountsByUser(String userLogin){
        List<AccountDTO> dtos = new ArrayList<>();
        List<Account> accounts = accountRepository.findAccountsByCustomUser_Login(userLogin);
        for (Account account : accounts) {
            dtos.add(new AccountDTO(account.getAccountNumber(), account.getMoney(), account.getCurrency().toString()));
        }
        return dtos;
    }

    @Transactional(readOnly = true)
    @Override
    public Account getAccountByNumber(Long accountNumber){
        return accountRepository.findAccountByAccountNumber(accountNumber);
    }

    @Transactional(readOnly = true)
    @Override
    public Long countAccounts() {
        return accountRepository.countAccountsByAccountNumberIsNotNull();
    }




}
