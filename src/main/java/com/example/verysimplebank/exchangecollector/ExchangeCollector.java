package com.example.verysimplebank.exchangecollector;

import com.example.verysimplebank.dto.ExchangeRateDTO;
import com.example.verysimplebank.model.ExchangeRate;
import com.example.verysimplebank.services.ExchangeService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExchangeCollector {

    public List<ExchangeRateDTO> collectExchange(String adress) {
        Gson gson = new GsonBuilder().create();
        ArrayList<ExchangeRateDTO> rates = new ArrayList<>();
        try {
            URL url = new URL(adress);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            try (InputStream is = http.getInputStream()) {
                byte[] buf = responseBodyToArray(is);
                String strBuf = new String(buf, StandardCharsets.UTF_8);
                StringBuilder builder = new StringBuilder(strBuf);
                builder.deleteCharAt(strBuf.length() - 1);
                builder.deleteCharAt(0);
                String [] str = builder.toString().split("[{]");
                for (String s : str) {
                    if (s.length() != 0) {
                        if (!s.contains("BTC")) {
                            StringBuilder finalBuilder = new StringBuilder(s);
                            finalBuilder.deleteCharAt(s.length() - 1);
                            String result = "{" + finalBuilder.toString();
                            System.out.println(finalBuilder.toString());
                            ExchangeRateDTO e = gson.fromJson(result, ExchangeRateDTO.class);

                            rates.add(e);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rates;
    }

    private static byte[] responseBodyToArray(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[10240];
        int r;

        do {
            r = is.read(buf);
            if (r > 0) bos.write(buf, 0, r);
        } while (r != -1);
        is.close();

        return bos.toByteArray();
    }
}
