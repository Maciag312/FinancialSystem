package com.kamilbartek.financial_system.jsons;

import java.util.ArrayList;
import java.util.List;

public class TransferJSON
{
    public boolean isFrom;
    public double amount;
    public Long reciever_account_id;
    public Long sender_account_id;
    public String currency;
    public String postTime;
}
