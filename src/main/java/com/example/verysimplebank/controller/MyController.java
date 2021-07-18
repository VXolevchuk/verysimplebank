package com.example.verysimplebank.controller;

import com.example.verysimplebank.dto.*;
import com.example.verysimplebank.dto.result.*;
import com.example.verysimplebank.model.Account;
import com.example.verysimplebank.model.Currency;
import com.example.verysimplebank.model.CustomUser;
import com.example.verysimplebank.security.TokenHandler;
import com.example.verysimplebank.services.AccountService;
import com.example.verysimplebank.services.ExchangeService;
import com.example.verysimplebank.services.TransactionService;
import com.example.verysimplebank.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
public class MyController {
    @Autowired
    private  TokenHandler handler;
    private final UserService userService;
    private final AccountService accountService;
    private final TransactionService transactionService;
    private final ExchangeService exchangeService;

    public MyController(UserService userService, AccountService accountService, TransactionService transactionService, ExchangeService exchangeService) {
        this.userService = userService;
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.exchangeService = exchangeService;
    }

    @GetMapping("/mainPage")
    public CustomUserDTO user() {
        User user = getCurrentUser();
        String login = user.getUsername();
        return new CustomUserDTO(login);
    }

    @GetMapping("token")
    public String  getToken() {
        return null;
    }


    @GetMapping("accounts")
    public List<AccountDTO> accounts() {
        User user = getCurrentUser();
        String login = user.getUsername();

        return accountService.getAccountsByUser(login);
    }

    @GetMapping("exchange")
    public List<ExchangeRateDTO> getExchangeRate() {
        return exchangeService.getActual();
    }


    @PostMapping("transaction")
    public ResponseEntity<ResultDTO> performTransaction(@RequestBody TransactionDTO transactionDTO) {
        User user = getCurrentUser();
        String login = user.getUsername();

        transactionService.performTransaction(transactionDTO.getSenderCardNumber(), transactionDTO.getReceiverCardNumber(),
                                               transactionDTO.getValue());
        return new ResponseEntity<>(new SuccessResult(), HttpStatus.OK);
    }

    @GetMapping("transactions")
    public List<TransactionToDisplayDTO> getTransactions() {
        User user = getCurrentUser();
        String login = user.getUsername();
        return transactionService.getAllByUser(login);
    }

    @PostMapping("createAccount")
    public ResponseEntity<ResultDTO> createAccount(@RequestBody AccountDTO accountDTO) {
        try {
            Currency currency = Currency.valueOf(accountDTO.getCurrency());
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new UnsupportedCurrencyResult(), HttpStatus.BAD_REQUEST);
        }

        User user = getCurrentUser();
        String login = user.getUsername();
        CustomUser customUser = userService.findByLogin(login);

        if (!accountService.existByNumber(accountDTO.getAccountNumber())) {
            Account account = new Account(accountDTO.getAccountNumber(), Currency.valueOf(accountDTO.getCurrency()));
            account.setCustomUser(customUser);
            accountService.addAccount(account);

            return new ResponseEntity<>(new SuccessResult(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new UnsuitableAccountNumberResult(), HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResultDTO> handleException() {
        return new ResponseEntity<>(new BadRequestResult(), HttpStatus.BAD_REQUEST);
    }


    private User getCurrentUser() {
        return (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }

    private boolean isAdmin(User user) {
        Collection<GrantedAuthority> roles = user.getAuthorities();

        for (GrantedAuthority auth : roles) {
            if ("ROLE_ADMIN".equals(auth.getAuthority()))
                return true;
        }

        return false;
    }
}
