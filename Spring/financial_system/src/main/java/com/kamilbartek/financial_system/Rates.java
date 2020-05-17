package com.kamilbartek.financial_system;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Rates {

        private HashMap<String, BigDecimal> rates = new HashMap<>();
        private String base;
        private Date date;

        public Rates(String base, Date date) {
            this.base = base;
            this.date = date;
        }

        public void clearRates(){
            rates.clear();
        }

        public HashMap<String, BigDecimal> getRates() {
            return rates;
        }
        public void addRate(String curName, BigDecimal curValue){
            this.rates.put(curName, curValue);
        }

        public void setRates(HashMap<String, BigDecimal> rates) {
            this.rates = rates;
        }

        public String getBase() {
            return base;
        }

        public void setBase(String base) {
            this.base = base;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }
    }


