package com.kamilbartek.financial_system;

import java.math.BigDecimal;

public class Currency {
    private String currencyName;
    private BigDecimal currencyValue;
    private String base = "EUR";

    public String getBase() {
        return base;
    }

    public Currency(){}

    public Currency(String currencyName, BigDecimal curreancyValue){
        this.currencyName = currencyName;
        this.currencyValue= curreancyValue;
    }

    public BigDecimal getCurrencyValue() {
        return currencyValue;
    }

    public void setCurrencyValue(BigDecimal currencyValue) {
        this.currencyValue = currencyValue;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }


    }

