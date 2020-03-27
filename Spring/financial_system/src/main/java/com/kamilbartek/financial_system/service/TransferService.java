package com.kamilbartek.financial_system.service;


import com.kamilbartek.financial_system.Cash;
import com.kamilbartek.financial_system.model.Account;
import com.kamilbartek.financial_system.model.Transfer;
import com.kamilbartek.financial_system.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

import java.math.BigDecimal;


@Service
public class TransferService {

    @Autowired
    private TransferRepository transferRepository;

    Transfer transfer = new Transfer();
    Account account = new Account();
    Boolean send(Account from, Account to, BigDecimal amount, String currency){

        if((account.getBilance().doubleValue() - amount.doubleValue()) >=  0) // amount.doubleValue()*currency powinno być
        {
            transfer.setAmount(amount);
            transfer.setCurrency(currency);
            transfer.setSender(from);
            transfer.setReciever(to);
            transferRepository.save(transfer);
            return true;
        }
        else
            return false;

    }

    Boolean sendOnExactelyTime(Account from, Account to,BigDecimal amount,Date postDate)
    {
        if((account.getBilance().doubleValue() - amount.doubleValue()) >= 0) {
            transfer.setAmount(amount);
            transfer.setSender(from);
            transfer.setReciever(to);
            transfer.setPost_date(postDate);
            return true;
        }
        else return false;

    }

}
