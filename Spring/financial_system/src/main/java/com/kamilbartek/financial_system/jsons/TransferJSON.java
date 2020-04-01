package com.kamilbartek.financial_system.jsons;

import java.util.ArrayList;
import java.util.List;

public class TransferJSON
{
    public Long sender_account_id;
    public Long reciever_account_id;
    public double amount;
    public String currency;


    public void setSender_account_id(Long sender_account_id) {
        this.sender_account_id = sender_account_id;
    }

    public void setReciever_account_id(Long reciever_account_id) {
        this.reciever_account_id = reciever_account_id;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getSender_account_id() {
        return sender_account_id;
    }

    public Long getReciever_account_id() {
        return reciever_account_id;
    }

    public String getCurrency() {
        return currency;
    }

    public double getAmount() {
        return amount;
    }
}
