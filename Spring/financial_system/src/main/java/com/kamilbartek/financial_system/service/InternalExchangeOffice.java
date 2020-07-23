package com.kamilbartek.financial_system.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kamilbartek.financial_system.Currency;
import com.kamilbartek.financial_system.Rates;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class InternalExchangeOffice {

    private static final InternalExchangeOffice exchangeInstance = null;

    private Rates rates;

    public boolean updateRates(String base) throws IOException {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Calendar calendarObj = Calendar.getInstance();

        URL url = new URL("https://api.exchangeratesapi.io/latest?base="+base+"&date="+dateFormat.format(calendarObj.getTime()));
        String readLine = null;
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
//        connection.setRequestProperty("base",base);
//        connection.setRequestProperty("date", dateFormat.format(calendarObj.getTime()));
        int responseCode = connection.getResponseCode();


        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((readLine = input.readLine()) != null) {
                response.append(readLine);
            }
            input.close();

            String jsonString = response.toString();

            ObjectMapper mapper = new ObjectMapper();

            //Rates base = mapper.readValue(jsonString, Rates.class);
            JsonParser parser = new JsonParser();
            JsonElement tree = parser.parse(jsonString);
            JsonObject jsonObject = tree.getAsJsonObject();
            JsonElement erates = parser.parse(jsonObject.get("rates").toString());
            JsonObject jobRates = erates.getAsJsonObject();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            try {
                rates = new Rates(jsonObject.get("base").getAsString(), format.parse(jsonObject.get("date").getAsString()));
                rates.clearRates();
                rates.addRate("PLN", BigDecimal.valueOf(jobRates.get("PLN").getAsDouble()));
                rates.addRate("USD", BigDecimal.valueOf(jobRates.get("USD").getAsDouble()));
                rates.addRate("ISK", BigDecimal.valueOf(jobRates.get("ISK").getAsDouble()));
                rates.addRate("GBP", BigDecimal.valueOf(jobRates.get("GBP").getAsDouble()));
                rates.addRate("JPY", BigDecimal.valueOf(jobRates.get("JPY").getAsDouble()));
                rates.addRate("CHF", BigDecimal.valueOf(jobRates.get("CHF").getAsDouble()));
                rates.addRate("EUR", BigDecimal.valueOf(jobRates.get("CHF").getAsDouble()));

                BigDecimal PLN = rates.getRates().get("PLN");
                BigDecimal USD = rates.getRates().get("USD");
                BigDecimal ISK = rates.getRates().get("ISK");
                BigDecimal GBP = rates.getRates().get("GBP");
                BigDecimal JPY = rates.getRates().get("JPY");
                BigDecimal CHF = rates.getRates().get("PLN");
                BigDecimal EUR = rates.getRates().get("EUR");

                System.out.println(rates.getRates().get("USD"));

            }catch(ParseException p){
                System.out.println(p);
            }

        } else {
            System.out.println("GET DOES'T WORK");
        }


        return true;
    }

    public BigDecimal exchangeValue(String currencyFrom, String currencyTo, BigDecimal value) throws IOException{
        return value.multiply(exchangeRate(currencyFrom,currencyTo));
    }
    public BigDecimal exchangeRate(String currencyFrom, String currencyTo) throws IOException {
        updateRates("EUR");
        BigDecimal CurrencyFrom = rates.getRates().get(currencyFrom);
        BigDecimal CurrencyTo = rates.getRates().get(currencyTo);
        if (CurrencyTo != BigDecimal.ZERO || CurrencyFrom != BigDecimal.ZERO) {
            // calculate and return rate
           // EUR/PLN * 1/(EUR/CHF) = PLN/CHF
         BigDecimal rate  = rates.getRates().get(currencyFrom).divide(rates.getRates().get(currencyTo));
            return rate;
        } else {
            return BigDecimal.ZERO;
        }

    }

    public Rates getRates() {
        return rates;
    }

    public InternalExchangeOffice() throws IOException {
        updateRates("EUR");
    }
    public static InternalExchangeOffice getInstance() throws IOException {
        if (exchangeInstance == null){
            return new InternalExchangeOffice();
        }

        return exchangeInstance;
    }

}