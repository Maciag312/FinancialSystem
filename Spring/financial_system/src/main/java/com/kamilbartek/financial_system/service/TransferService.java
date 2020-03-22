package com.kamilbartek.financial_system.service;


import com.kamilbartek.financial_system.Cash;
import com.kamilbartek.financial_system.model.Account;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransferService {


    Boolean send(Account from, Account to, Cash amount){
        //TODO
        return true;
    }
    Boolean sendOnExactelyTime(Account from, Account to,Cash amount){
        //TODO
        return true;
    }

}
