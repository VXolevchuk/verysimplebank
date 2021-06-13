package com.example.verysimplebank.services;

import com.example.verysimplebank.dto.TransactionToDisplayDTO;
import com.example.verysimplebank.model.*;
import com.example.verysimplebank.repos.TransactionRepository;
import com.example.verysimplebank.services.converter.CurrencyConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService{
    private final TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;
    @Autowired
    private CurrencyConverter converter;


    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    @Override
    public void performTransaction(Long performerNumber, Long receiverNumber, double value) {
        Account sender = accountService.getAccountByNumber(performerNumber);
        Account receiver = accountService.getAccountByNumber(receiverNumber);
        String convertedCurrency = sender.getCurrency().toString();
        String baseCurrency = receiver.getCurrency().toString();
        double senderMoney = sender.getMoney();
        double receiverMoney = receiver.getMoney();
        if (senderMoney >= value) {
            if (convertedCurrency.equals(baseCurrency)) {
                receiver.setMoney(receiverMoney + value);
                sender.setMoney(senderMoney - value);
            } else {
                double convertedValue = converter.getRatio(convertedCurrency, baseCurrency, value);
                receiver.setMoney(receiverMoney + convertedValue);
                sender.setMoney(senderMoney - value);
            }
        }


        Date date = new Date();
        Transaction transaction = new Transaction(date, sender.getCurrency(), TransactionType.PERFORMED, value);
        Transaction transaction1 = new Transaction(date, sender.getCurrency(), TransactionType.RECEIVED, value);
        //transaction.setAccount(sender);
        transaction.setRelatedAccountNumber(receiverNumber);
        //transaction1.setAccount(receiver);
        transaction1.setRelatedAccountNumber(performerNumber);

        sender.setTransaction(transaction);
        receiver.setTransaction(transaction1);

        accountService.addAccount(sender);
        accountService.addAccount(receiver);
    }


    @Transactional(readOnly = true)
    @Override
    public List<TransactionToDisplayDTO> getAllByUser(String login){
        List<TransactionToDisplayDTO> dtos = new ArrayList<>();
        List<Transaction> transactions = transactionRepository.getTransactionDTO(login);
        for (Transaction t : transactions) {
            if (t.getType() == TransactionType.PERFORMED) {
                TransactionToDisplayDTO dto = new TransactionToDisplayDTO(t.getDate().toString(), t.getValue(), t.getAccount().getAccountNumber(), t.getRelatedAccountNumber(), t.getCurrency(), t.getType());
                dtos.add(dto);
            } else {
                TransactionToDisplayDTO dto = new TransactionToDisplayDTO(t.getDate().toString(), t.getValue(), t.getRelatedAccountNumber(), t.getAccount().getAccountNumber(), t.getCurrency(), t.getType());
                dtos.add(dto);
            }
        }
        return  dtos;
    }



}
