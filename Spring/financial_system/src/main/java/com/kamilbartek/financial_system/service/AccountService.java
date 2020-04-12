package com.kamilbartek.financial_system.service;

import com.kamilbartek.financial_system.jsons.TransferJSON;
import com.kamilbartek.financial_system.model.Account;
import com.kamilbartek.financial_system.model.Transfer;
import com.kamilbartek.financial_system.model.User;
import com.kamilbartek.financial_system.repository.AccountRepository;
import com.kamilbartek.financial_system.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.math.BigDecimal;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;


    public Boolean createAccount(User user, String currency)
    {
        Account newAccount = new Account();
        newAccount.setUser(user);
        newAccount.setBilance(BigDecimal.valueOf(1000.5));
        newAccount.setAccount_creation_date(Calendar.getInstance().getTime());
        newAccount.setCurrency(currency);
        accountRepository.save(newAccount);
        return true;
    }

    @Autowired
    private TransferRepository transferRepository;


     List<TransferJSON> getNLastTransfers(int n, Long account_id){
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
        for(int i = 0; i<transfersFromSenderList.size();i++)
            concated.add(transfersFromReceiverList.get(i));
        Collections.sort(concated, Comparator.comparing(Transfer::getRecieve_date).reversed());
        List<TransferJSON> transferJSONList = new ArrayList<>();
        for(int i=0; i<n;i++){
           transferJSON = new TransferJSON();
           Transfer t = concated.get(i);
           transferJSON.amount = t.getAmount().doubleValue();
           transferJSON.currency = t.getCurrency();
           transferJSON.reciever_account_id = t.getReciever().getAccountId();
           transferJSON.sender_account_id = t.getSender().getAccountId();
           transferJSONList.add(transferJSON);
        }
         return transferJSONList;
     }
     BigDecimal getBilance(Long account_id){
         Account account = accountRepository.findById(account_id).orElse(null);
         return account!=null ? account.getBilance() : BigDecimal.ZERO;
     }






}
