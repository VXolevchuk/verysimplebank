package com.example.verysimplebank.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class ExchangeRateDTO {
    private String ccy;
    private String base_ccy;
    private double buy;
    private double sale;

    public ExchangeRateDTO(String ccy, String base_ccy, double buy, double sale) {
        this.ccy = ccy;
        this.base_ccy = base_ccy;
        this.buy = buy;
        this.sale = sale;
    }



}
