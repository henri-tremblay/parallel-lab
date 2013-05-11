package org.henri.stockpricer.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.*;

@Service
public class Pricer {

    private final Map<String, BigDecimal> stocks = new HashMap<>();

    public Pricer() {
        try(BufferedReader in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/stocks.txt")))) {
            String line = in.readLine();
            String[] tickers = line.split(";");
            Random rand = new Random();
            for(String t : tickers) {
                BigDecimal bd = new BigDecimal(rand.nextInt(200));
                stocks.put(t, bd);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public BigDecimal getPrice(String ticker) {
        return stocks.get(ticker);
    }

    public List<String> listAllTickers() {
        List<String> all = new ArrayList<>(stocks.size());
        for(String s : stocks.keySet()) {
            all.add(s);
        }
        Collections.sort(all);
        return all;
    }
}
