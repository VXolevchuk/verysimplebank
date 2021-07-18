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
        if (converted.equals(uah) || base.equals(uah)) {
            double ratio;
            if (converted.equals(uah)) {
                ratio = getBuyRatioToUAH(base);
                returnedValue = value/ratio;
            }
            if (base.equals(uah)) {
                ratio = getSaleRatioToUAH(converted);
                returnedValue = value*ratio;
            }
        } else {
            double buyRatioForUSD = getBuyRatioToUAH(usd);
            double saleRatioForUSD = getSaleRatioToUAH(usd);

            double buyRatioForEUR = getBuyRatioToUAH(eur);
            double saleRatioForEUR = getSaleRatioToUAH(eur);

            double saleRatio = saleRatioForEUR/saleRatioForUSD;
            double buyRatio = buyRatioForEUR/buyRatioForUSD;

            if (converted.equals(usd)) {
                returnedValue = value/buyRatio;
            }
            if (converted.equals(eur)) {
                returnedValue = value*saleRatio;
            }
        }
        return returnedValue;
    }

    private double getBuyRatioToUAH(String currency) {
        ExchangeRate exchangeRate = exchangeService.getLastByCcy(currency);
        return exchangeRate.getBuy();
    }

    private double getSaleRatioToUAH(String currency) {
        ExchangeRate exchangeRate = exchangeService.getLastByCcy(currency);
        return  exchangeRate.getSale();
    }
}
