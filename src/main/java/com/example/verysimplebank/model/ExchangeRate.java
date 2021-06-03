package com.example.verysimplebank.model;


import com.example.verysimplebank.dto.ExchangeRateDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "ExchangeRates")
@Data @NoArgsConstructor
public class ExchangeRate {
    @Id
    @GeneratedValue
    private Long id;

    private String date;

    private String ccy;
    private String base_ccy;
    private double buy;
    private double sale;

    public ExchangeRate(String date, String ccy, String base_ccy, double buy, double sale) {
        this.date = date;
        this.ccy = ccy;
        this.base_ccy = base_ccy;
        this.buy = buy;
        this.sale = sale;
    }

    public ExchangeRate(String ccy, String base_ccy, double buy, double sale) {
        this.ccy = ccy;
        this.base_ccy = base_ccy;
        this.buy = buy;
        this.sale = sale;
    }

    public static ExchangeRate of(String ccy, String base_ccy, double buy, double sale) {
        return new ExchangeRate(ccy, base_ccy, buy, sale);
    }

    public static ExchangeRate fromDTO(ExchangeRateDTO rateDTO) {
        return ExchangeRate.of(rateDTO.getCcy(), rateDTO.getBase_ccy(), rateDTO.getBuy(), rateDTO.getSale());
    }


}
