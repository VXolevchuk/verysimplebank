package com.example.verysimplebank.services;

import com.example.verysimplebank.dto.ExchangeRateDTO;
import com.example.verysimplebank.model.ExchangeRate;

import java.util.List;

public interface ExchangeService {
    void addExchange(ExchangeRate exchange);
    List<ExchangeRate> getAll();
    List<ExchangeRate> getAllByCcy(String ccy);

    List<ExchangeRateDTO> getActual();
    ExchangeRate getLastByCcy(String ccy);
}
