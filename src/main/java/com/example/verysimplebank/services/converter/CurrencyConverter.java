package com.example.verysimplebank.services.converter;

import com.example.verysimplebank.model.ExchangeRate;
import com.example.verysimplebank.services.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CurrencyConverter {
    private final String uah = "UAH";
    private final String usd = "USD";
    private final String eur = "EUR";

    @Autowired
    private ExchangeService exchangeService;

    public double getRatio(String convertedCurrency, String baseCurrency, double value) {
        double valueToRerturn = 0;
        double ratio = 0;
        if (convertedCurrency.equals(uah) || baseCurrency.equals(uah)) {
            if (convertedCurrency.equals(usd)) {
                ExchangeRate exchangeRate = exchangeService.getLastByCcy(usd);
                ratio = (exchangeRate.getBuy() + exchangeRate.getSale())/2;
                valueToRerturn = value*ratio;

            }
            if (convertedCurrency.equals(eur)) {
                ExchangeRate exchangeRate = exchangeService.getLastByCcy(eur);
                ratio = (exchangeRate.getBuy() + exchangeRate.getSale())/2;
                valueToRerturn = value*ratio;
            }
            if (baseCurrency.equals(usd)) {
                ExchangeRate exchangeRate = exchangeService.getLastByCcy(usd);
                ratio = (exchangeRate.getBuy() + exchangeRate.getSale())/2;
                valueToRerturn = value/ratio;
            }
            if (baseCurrency.equals(eur)) {
                ExchangeRate exchangeRate = exchangeService.getLastByCcy(eur);
                ratio = (exchangeRate.getBuy() + exchangeRate.getSale())/2;
                valueToRerturn = value/ratio;
            }
        } else {
            ExchangeRate exchangeRate1 =  exchangeService.getLastByCcy(usd);
            ExchangeRate exchangeRate2 = exchangeService.getLastByCcy(eur);
            double middle1 = (exchangeRate1.getBuy() + exchangeRate1.getSale())/2;
            double middle2 = (exchangeRate2.getBuy() + exchangeRate2.getSale())/2;
            ratio = middle2/middle1;
            if (convertedCurrency.equals(usd)) {
                valueToRerturn = value/ratio;
            }
            if (convertedCurrency.equals(eur)) {
                ExchangeRate exchangeRate = exchangeService.getLastByCcy(eur);
                ratio = (exchangeRate.getBuy() + exchangeRate.getSale())/2;
                valueToRerturn = value*ratio;
            }
        }
        return valueToRerturn;
    }
}
