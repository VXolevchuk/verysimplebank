package com.example.verysimplebank.services;

import com.example.verysimplebank.dto.TransactionDTO;
import com.example.verysimplebank.dto.TransactionToShowDTO;
import com.example.verysimplebank.model.Account;
import com.example.verysimplebank.model.CustomUser;
import com.example.verysimplebank.model.Transaction;
import com.example.verysimplebank.repos.TransactionRepository;
import com.example.verysimplebank.services.converter.CurrencyConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
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
        SimpleDateFormat sdf = new SimpleDateFormat("y MM dd h:mm");
        String finalDate = sdf.format(date);
        Transaction transaction = new Transaction(finalDate, value, convertedCurrency);

        CustomUser user = userService.findByLogin(sender.getCustomUser().getLogin());
        user.addTransaction(transaction);
        userService.addUser(user);

        receiver.setReceivedTransaction(transaction);
        accountService.addAccount(sender);
        accountService.addAccount(receiver);
    }


    @Transactional(readOnly = true)
    @Override
    public List<TransactionToShowDTO> getAllByUser(String login){
        List<TransactionToShowDTO> dtos = new ArrayList<>();
        List<Transaction> transactions = transactionRepository.getTransactionDTO(login);
        for (Transaction t : transactions) {
            TransactionToShowDTO dto = new TransactionToShowDTO(t.getDate(), t.getValue(), t.getRecipient().getAccountNumber(), t.getSender().getLogin(), t.getCurrency());
            dtos.add(dto);
        }
        return  dtos;
    }



}
