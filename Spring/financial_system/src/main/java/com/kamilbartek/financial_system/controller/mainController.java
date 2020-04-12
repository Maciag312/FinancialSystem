package com.kamilbartek.financial_system.controller;


import com.kamilbartek.financial_system.jsons.BilanceInfoJSON;
import com.kamilbartek.financial_system.jsons.ClientJSON;
import com.kamilbartek.financial_system.jsons.TransferJSON;
import com.kamilbartek.financial_system.model.Account;
import com.kamilbartek.financial_system.model.User;
import com.kamilbartek.financial_system.repository.AccountRepository;
import com.kamilbartek.financial_system.repository.UserRepository;
import com.kamilbartek.financial_system.service.AccountService;
import com.kamilbartek.financial_system.service.TransferService;
import com.kamilbartek.financial_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class mainController {


    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userSerivce;


    @Autowired
    AccountService accountService;


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
        if(account_from==null||account_to==null) return false;
        return transferService.send(account_from, account_to, BigDecimal.valueOf(transferJSON.amount), transferJSON.currency);
    }

    @PostMapping(value = "/createClient", consumes = "application/json")
    public boolean createClient(@RequestBody ClientJSON clientJSON){
        return userSerivce.createUser(clientJSON.username,clientJSON.password,clientJSON.name,clientJSON.surname,clientJSON.phone_number,clientJSON.address,clientJSON.country,clientJSON.identity_card_number,clientJSON.date_of_birth,clientJSON.email_address);
    }

    @PostMapping("/createAccount/{user_id}/{currency}")
    public boolean createAccount(@PathVariable Long user_id, @PathVariable String currency){
        User client = userRepository.findById(user_id).orElse(null);
        if(client==null) return false;
        return accountService.createAccount(client, currency);
    }


    @GetMapping("/getUsers")
    public List<User> getUsers(){
       return userRepository.findAll();
    }

    @GetMapping("/getAccounts")
    public List<Account> getAccounts(){
        return accountRepository.findAll();
    }

    @GetMapping("/getBilanceInfo/{account_id}")
    public BilanceInfoJSON getBilanceInfo(@PathVariable Long account_id){
        BilanceInfoJSON bilanceInfoJSON = new BilanceInfoJSON();

        Account account = accountRepository.findById(account_id).orElse(null);
        if(account==null) return bilanceInfoJSON;
        bilanceInfoJSON.name = account.getUser().getName();
        bilanceInfoJSON.surname = account.getUser().getSurname();
        bilanceInfoJSON.currentBilance = account.getBilance().longValue();
        //bilanceInfoJSON.recentTenTransfers = accountService.getNLastTransfers(10, Client client)
        return bilanceInfoJSON;
    }



}
