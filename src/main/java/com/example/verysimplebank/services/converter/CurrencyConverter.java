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

    public double convertCurrency(String converted, String base, double value) {
        double returnedValue = 0;
        double ratio;
        if (converted.equals(uah) || base.equals(uah)) {
            if (converted.equals(uah)) {
                ratio = getRatioToUAH(base);
                returnedValue = value/ratio;
            }
            if (base.equals(uah)) {
                ratio = getRatioToUAH(converted);
                returnedValue = value*ratio;
            }
        } else {
            double ratioForUSD = getRatioToUAH(usd);
            double ratioForEUR = getRatioToUAH(eur);
            ratio = ratioForEUR/ratioForUSD;
            if (converted.equals(usd)) {
                returnedValue = value/ratio;
            }
            if (converted.equals(eur)) {
                returnedValue = value*ratio;
            }
        }
        return returnedValue;
    }

    private double getRatioToUAH(String currency) {
        ExchangeRate exchangeRate = exchangeService.getLastByCcy(currency);
        return (exchangeRate.getBuy() + exchangeRate.getSale())/2;
    }
}
