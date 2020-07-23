package com.kamilbartek.financial_system.service;


import com.kamilbartek.financial_system.Cash;
import com.kamilbartek.financial_system.jsons.TransferJSON;
import com.kamilbartek.financial_system.model.Account;
import com.kamilbartek.financial_system.model.PendingTransfer;
import com.kamilbartek.financial_system.model.Transfer;
import com.kamilbartek.financial_system.repository.AccountRepository;
import com.kamilbartek.financial_system.repository.PendingTransferRepository;
import com.kamilbartek.financial_system.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import java.math.BigDecimal;


@Service
public class TransferService {

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private PendingTransferRepository pendingTransferRepository;

    @Autowired
    EmailService emailService;



    @Autowired
    private AccountRepository accountRepository;


    public Boolean createTransferAndPassCode(Account from, Account to, BigDecimal amount, String currency){
        PendingTransfer pendingTransfer = new PendingTransfer();

        pendingTransfer.setSender(from);
        pendingTransfer.setReciever(to);
        pendingTransfer.setAmount(amount);
        pendingTransfer.setCurrency(currency);
        pendingTransfer.setRecieve_date(Calendar.getInstance().getTime());
        pendingTransfer.setPost_date(Calendar.getInstance().getTime());

        pendingTransferRepository.save(pendingTransfer);

        String passCode = String.valueOf(Math.random() * ( 999999 - 0));
        while (passCode.length()!=6)
            passCode = "0" + passCode;

        pendingTransfer.setCode(passCode);

        if(emailService.passCodeToUsersMail(passCode, from.getUser().getEmail_address()))
            return false;


        if(pendingTransfer.getAmount().doubleValue() <= 0)
            return false;

        pendingTransferRepository.save(pendingTransfer);


        return true;
    }

    public Boolean validateCodeAndSendTransfer(String transferCode, Account from, Account to){
        PendingTransfer pendingTransfer = pendingTransferRepository.findByCodeAndSender(transferCode,from).orElse(null);
        if(pendingTransfer==null)
            return false;

        Transfer transfer = new Transfer();

        transfer.setAmount(pendingTransfer.getAmount());
        transfer.setReciever(pendingTransfer.getReciever());
        transfer.setSender(pendingTransfer.getSender());
        transfer.setCurrency(pendingTransfer.getCurrency());
        transfer.setCreation_time(Calendar.getInstance().getTime());

        if((from.getBilance().doubleValue() - pendingTransfer.getAmount().doubleValue()) >=  0)
        {
            transfer.setSent(true);
            transferRepository.save(transfer);

            from.setBilance(from.getBilance().subtract(pendingTransfer.getAmount()));

            // 1. Exchange office get Instance
            // 2. to.getBilnace().add(office.exchange(amount, currencyFrom, currencyTo));
            to.setBilance(to.getBilance().add(pendingTransfer.getAmount()));

            accountRepository.save(from);
            accountRepository.save(to);

            return true;
        }
        else
            return false;




    }





}
