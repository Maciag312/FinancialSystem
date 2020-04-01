package com.kamilbartek.financial_system.controller;


import com.kamilbartek.financial_system.jsons.BilanceInfoJSON;
import com.kamilbartek.financial_system.jsons.ClientJSON;
import com.kamilbartek.financial_system.jsons.TransferJSON;
import com.kamilbartek.financial_system.model.Account;
import com.kamilbartek.financial_system.model.Client;
import com.kamilbartek.financial_system.repository.AccountRepository;
import com.kamilbartek.financial_system.repository.ClientRepository;
import com.kamilbartek.financial_system.service.ClientService;
import com.kamilbartek.financial_system.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class mainController {


    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ClientService clientService;

    @Autowired
    TransferService transferService;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "Greetings " + name + " !";
    }


    @PostMapping("/transfer")
    public boolean transfer(@RequestBody TransferJSON transferJSON){
        Account account_from = accountRepository.findById(transferJSON.sender_account_id).orElse(null);
        Account account_to = accountRepository.findById(transferJSON.reciever_account_id).orElse(null);
        if(account_from!=null||account_to!=null) return false;
        return transferService.send(account_from, account_to, BigDecimal.valueOf(transferJSON.amount), transferJSON.currency);
    }

    @PostMapping("/createClient")
    public boolean createClient(@RequestBody ClientJSON clientJSON){
        return clientService.newClient(clientJSON.name,clientJSON.surname,clientJSON.username,clientJSON.password,clientJSON.phone_number,clientJSON.address,clientJSON.country,clientJSON.identity_card_number,clientJSON.date_of_birth,clientJSON.email_address);
    }

    @PostMapping("/createAccount/{client_id}/{currency}")
    public boolean createAccount(@PathVariable Long client_id, @PathVariable String currency){
        Client client = clientRepository.findById(client_id).orElse(null);
        if(client==null) return false;
        return clientService.createAccount(client, currency);
    }

    @GetMapping("/getBilanceInfo/{account_id}")
    public BilanceInfoJSON getBilanceInfo(@PathVariable Long account_id){
        BilanceInfoJSON bilanceInfoJSON = new BilanceInfoJSON();

        Account account = accountRepository.findById(account_id).orElse(null);
        if(account==null) return bilanceInfoJSON;
        bilanceInfoJSON.name = account.getClient().getName();
        bilanceInfoJSON.surname = account.getClient().getSurname();
        bilanceInfoJSON.currentBilance = account.getBilance().longValue();
        bilanceInfoJSON.recentTenTransfers = accountService.getNLastTransfers()


    }

}
