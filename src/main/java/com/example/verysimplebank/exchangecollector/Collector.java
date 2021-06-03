package com.example.verysimplebank.exchangecollector;

import com.example.verysimplebank.dto.ExchangeRateDTO;
import com.example.verysimplebank.model.ExchangeRate;
import com.example.verysimplebank.services.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@EnableScheduling
public class Collector {
    private final String ADDRESS = "https://api.privatbank.ua/p24api/pubinfo?exchange&json&coursid=11";
    @Autowired
    private ExchangeService exchangeService;
    @Autowired
    private ExchangeCollector collector;

    @Scheduled(cron = "59 59 23 * * ?")
    public void collect() {
      List<ExchangeRateDTO> rateDTOS = collector.collectExchange(ADDRESS);
      for (ExchangeRateDTO rateDTO : rateDTOS) {
          ExchangeRate rate = ExchangeRate.fromDTO(rateDTO);

          Date date = new Date();
          SimpleDateFormat sdf = new SimpleDateFormat("y MM dd");
          String dateOfRate = sdf.format(date);
          rate.setDate(dateOfRate);

          exchangeService.addExchange(rate);
      }
    }
}
