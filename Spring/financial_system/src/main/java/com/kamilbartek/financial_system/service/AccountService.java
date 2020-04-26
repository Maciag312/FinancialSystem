package com.kamilbartek.financial_system.service;

import com.kamilbartek.financial_system.jsons.TransferJSON;
import com.kamilbartek.financial_system.model.Account;
import com.kamilbartek.financial_system.model.Transfer;
import com.kamilbartek.financial_system.model.User;
import com.kamilbartek.financial_system.repository.AccountRepository;
import com.kamilbartek.financial_system.repository.TransferRepository;
import com.kamilbartek.financial_system.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.math.BigDecimal;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;


    private Logger logger = LoggerFactory.getLogger(AccountService.class);


    public Boolean createAccount(User user, String currency)
    {
        Account newAccount = new Account();
        newAccount.setUser(user);
        newAccount.setBilance(BigDecimal.valueOf(1000.5));
        newAccount.setAccount_creation_date(Calendar.getInstance().getTime());
        newAccount.setCurrency(currency);
        accountRepository.save(newAccount);
//        user.setAccounts(accountRepository.f);
        userRepository.save(user);
        return true;
    }

    @Autowired
    private TransferRepository transferRepository;


    public List<TransferJSON> getNLastTransfers(int n, Long account_id){
         TransferJSON transferJSON;
         List<TransferJSON> transferList = new ArrayList<>();

         // 1. wyciagnac z repo transfery przychodzace z tym kontem
         // 2. wyciagnac z repo transfery wychodzace
         // 3. polaczyc je i posortowac wg. daty
         // 4. wyciagnac n najwczesniejszych
         // 5. mapping transfers na transfersJSON i return listy

        Account account = accountRepository.findById(account_id).orElse(null);
        List<Transfer> transfersFromSenderList = transferRepository.findAllBySender(account);
        List<Transfer> transfersFromReceiverList = transferRepository.findAllByReciever(account);
        List<Transfer> concated = transfersFromSenderList;



        for(int i = 0; i<transfersFromReceiverList.size();i++)
            concated.add(transfersFromReceiverList.get(i));

        concated.sort((Transfer a, Transfer b)-> a.getPost_date().compareTo(b.getPost_date()));
        Collections.reverse(concated);

        List<TransferJSON> transferJSONList = new ArrayList<>();
        int min = n<=concated.size()?n:concated.size();
        for(int i=0; i<min;i++){
           transferJSON = new TransferJSON();
           Transfer t = concated.get(i);
           transferJSON.isFrom = account_id==t.getReciever().getAccountId()? true : false;
           transferJSON.amount = t.getAmount().doubleValue();
           if(account_id!=t.getReciever().getAccountId()) transferJSON.amount = -transferJSON.amount;
           transferJSON.currency = t.getCurrency();
           transferJSON.reciever_account_id = t.getReciever().getAccountId();
           transferJSON.sender_account_id = t.getSender().getAccountId();
           transferJSON.postTime = t.getPost_date().toString();
           transferJSONList.add(transferJSON);
        }

        return transferJSONList;
     }

     public List<Account> getAccountsByUser(User user){
        return accountRepository.findAllByUser(user);
     }



     BigDecimal getBilance(Long account_id){
         Account account = accountRepository.findById(account_id).orElse(null);
         return account!=null ? account.getBilance() : BigDecimal.ZERO;
     }






}
