package com.kamilbartek.financial_system.service;

import com.kamilbartek.financial_system.model.Account;
import com.kamilbartek.financial_system.model.Client;
import com.kamilbartek.financial_system.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    Account newAccount;
    Boolean createAccount(Date account_creation_date, String currency)
    {
    newAccount = new Account();
    newAccount.setClient(new Client());
    newAccount.setBilance(null);
    newAccount.setAccount_creation_date(account_creation_date);
    newAccount.setCurrency(currency);
        return true;

    }

}
