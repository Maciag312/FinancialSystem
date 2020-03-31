package com.kamilbartek.financial_system.service;


import com.kamilbartek.financial_system.Cash;
import com.kamilbartek.financial_system.jsons.TransferJSON;
import com.kamilbartek.financial_system.model.Account;
import com.kamilbartek.financial_system.model.Transfer;
import com.kamilbartek.financial_system.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

import java.math.BigDecimal;
import java.util.List;


@Service
public class TransferService {

    @Autowired
    private TransferRepository transferRepository;

    Transfer transfer;
    Account account ;
    public Boolean send(Account from, Account to, BigDecimal amount, String currency)
    {
        transfer = new Transfer();
        account = new Account();

        transfer.setSender(from);
        transfer.setReciever(to);
        transfer.setAmount(amount);
        transfer.setCurrency(currency);

        if(amount.doubleValue() < 0)
            return false;

        if((from.getBilance().doubleValue() - amount.doubleValue()) >=  0)
        {
            transferRepository.save(transfer);
            return true;
        }
        else
            return false;

    }

    public Boolean sendOnExactelyTime(Account from, Account to,BigDecimal amount,Date postDate,Date recieveDate)
    {
        transfer = new Transfer();
        account = new Account();

        transfer.setAmount(amount);
        transfer.setSender(from);
        transfer.setReciever(to);
        transfer.setPost_date(postDate);
        transfer.setRecieve_date(recieveDate);

        if(amount.doubleValue() <= 0)
            return false;

        if((from.getBilance().doubleValue() - amount.doubleValue()) >0) {
            transferRepository.save(transfer);
            return true;
        }
        else
            return false;

    }


}
