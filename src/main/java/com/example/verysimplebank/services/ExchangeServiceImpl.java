package com.example.verysimplebank.services;

import com.example.verysimplebank.dto.ExchangeRateDTO;
import com.example.verysimplebank.model.ExchangeRate;
import com.example.verysimplebank.repos.ExchangeRateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExchangeServiceImpl implements ExchangeService{
    private final ExchangeRateRepository rateRepository;

    public ExchangeServiceImpl(ExchangeRateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    @Transactional
    @Override
    public void addExchange(ExchangeRate exchange) {
        rateRepository.save(exchange);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ExchangeRate> getAll() {
        return rateRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<ExchangeRate> getAllByCcy(String ccy) {
        return rateRepository.findExchangeRateByCcy(ccy);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ExchangeRateDTO> getActual() {
        List<ExchangeRateDTO> dtos = new ArrayList<>();
        List<ExchangeRate> exchangeRates = rateRepository.findAll();
        for (int i = exchangeRates.size(); i > exchangeRates.size() - 3; i--) {
            ExchangeRate rate = exchangeRates.get(i - 1);
            dtos.add(new ExchangeRateDTO(rate.getCcy(), rate.getBase_ccy(), rate.getBuy(), rate.getSale()));
        }
        return dtos;
    }

    @Transactional(readOnly = true)
    @Override
    public ExchangeRate getLastByCcy(String ccy) {
        List<ExchangeRate> rates = getAllByCcy(ccy);
        return rates.get(rates.size() - 1);
    }
}
