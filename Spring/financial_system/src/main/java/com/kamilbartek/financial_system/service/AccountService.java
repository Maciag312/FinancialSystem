package com.kamilbartek.financial_system.service;

import com.kamilbartek.financial_system.jsons.TransferJSON;
import com.kamilbartek.financial_system.model.Account;
import com.kamilbartek.financial_system.model.Client;
import com.kamilbartek.financial_system.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    Account newAccount;
    public Boolean createAccount(Date account_creation_date, String currency)
    {
    newAccount = new Account();
    newAccount.getAccountId();
    newAccount.setClient(new Client());
    newAccount.setBilance(null);
    newAccount.setAccount_creation_date(account_creation_date);
    newAccount.setCurrency(currency);
        return true;
    }
    TransferJSON transferJSON;
     List<TransferJSON> transferList;

    Boolean createTransferList(Long sender_account_id, Long reciever_account_id, double amount, String currency)
    {
        transferList = new ArrayList<>();
        transferJSON.setSender_account_id(sender_account_id);
        transferJSON.setReciever_account_id(reciever_account_id);
        transferJSON.setAmount(amount);
        transferJSON.setCurrency(currency);

        transferList.add(transferJSON);
        return true;
    }




}
