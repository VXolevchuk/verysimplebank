package com.example.verysimplebank;

import com.example.verysimplebank.dto.ExchangeRateDTO;
import com.example.verysimplebank.exchangecollector.Collector;
import com.example.verysimplebank.exchangecollector.ExchangeCollector;
import com.example.verysimplebank.model.ExchangeRate;
import com.example.verysimplebank.repos.ExchangeRateRepository;
import com.example.verysimplebank.services.ExchangeService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExchangeCollectionTest {
    @Autowired
    private ExchangeService exchangeService;

    @MockBean
    private ExchangeRateRepository exchangeRepository;

    @Autowired
    private ExchangeCollector exchangeCollector;

    @Before
    public void before() {
        Mockito.reset(exchangeRepository);
    }

    @Test
    public void collectingTest(){
        String ADDRESS = "https://api.privatbank.ua/p24api/pubinfo?exchange&json&coursid=11";
        List<ExchangeRateDTO> rates = exchangeCollector.collectExchange(ADDRESS);
        Assert.assertEquals(3, rates.size());
        
        for (ExchangeRateDTO rateDTO : rates) {
            ExchangeRate rate = ExchangeRate.fromDTO(rateDTO);

            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("y MM dd");
            String dateOfRate = sdf.format(date);
            rate.setDate(dateOfRate);

            exchangeService.addExchange(rate);

            Mockito.verify(exchangeRepository, Mockito.times(1)).save(rate);
        }

    }

}
