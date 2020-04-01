package com.kamilbartek.financial_system.service;

import com.kamilbartek.financial_system.model.Account;
import com.kamilbartek.financial_system.model.User;
import com.kamilbartek.financial_system.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Boolean createAccount(User user, String currency)
    {
        Account newAccount = new Account();
        newAccount.setUser(user);
        newAccount.setBilance(BigDecimal.ZERO);
        newAccount.setAccount_creation_date(Calendar.getInstance().getTime());
        newAccount.setCurrency(currency);
        return true;

    }

}
