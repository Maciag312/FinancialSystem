package com.kamilbartek.financial_system.controller;


import com.kamilbartek.financial_system.jsons.BilanceInfoJSON;
import com.kamilbartek.financial_system.jsons.ClientJSON;
import com.kamilbartek.financial_system.jsons.TransferJSON;
import com.kamilbartek.financial_system.model.Account;
import com.kamilbartek.financial_system.model.Transfer;
import com.kamilbartek.financial_system.model.User;
import com.kamilbartek.financial_system.repository.AccountRepository;
import com.kamilbartek.financial_system.repository.UserRepository;
import com.kamilbartek.financial_system.service.AccountService;
import com.kamilbartek.financial_system.service.TransferService;
import com.kamilbartek.financial_system.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name, Authentication authentication) {
        return "Greetings " + authentication.getName() + name + " !";
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

    @GetMapping("/getNLastTransfers/{n}/{account_id}")
    public List<TransferJSON> getNLastTransfers(@PathVariable Integer n, @PathVariable Long account_id){
        return accountService.getNLastTransfers(n.intValue(), account_id);
    }

    Logger logger = LoggerFactory.getLogger(mainController.class);


    @GetMapping("/getBilanceInfo/{account_id}")
    public BilanceInfoJSON getBilanceInfo(@PathVariable Long account_id){



        BilanceInfoJSON bilanceInfoJSON = new BilanceInfoJSON();

        Account account = accountRepository.findById(account_id).orElse(null);
        if(account==null) return bilanceInfoJSON;

        logger.info(String.valueOf(account.getUser().getId()));


        bilanceInfoJSON.name = account.getUser().getName();
        bilanceInfoJSON.surname = account.getUser().getSurname();
        bilanceInfoJSON.currentBilance = account.getBilance().doubleValue();
        //bilanceInfoJSON.recentTenTransfers = accountService.getNLastTransfers(10, Client client)
        return bilanceInfoJSON;
    }

    @GetMapping("/getPrincipalBilanceInfo")
    public BilanceInfoJSON getPrincipalBilanceInfo(Authentication authentication){
        if(authentication.getPrincipal() instanceof User){
            User user = (User)authentication.getPrincipal();
            BilanceInfoJSON bilanceInfoJSON = new BilanceInfoJSON();
            Account account = accountRepository.findAllByUser(user).get(0);
            if(account==null){
                logger.error("getPrincipalBilanceInfo: Found account is null");
                return null;
            }
            bilanceInfoJSON.currentBilance = account.getBilance().doubleValue();
            bilanceInfoJSON.name = user.getName();
            bilanceInfoJSON.surname = user.getSurname();
            bilanceInfoJSON.currency = account.getCurrency();
            return bilanceInfoJSON;

        }else{
            logger.error("getPrincipalBilanceInfo: Principal is not instance of user");
            return null;
        }

    }

    @GetMapping("/getPrincipalNLastTransfers/{n}")
    public List<TransferJSON> getPrincipalNLastTransfers(@PathVariable Integer n, Authentication authentication){

        if(authentication.getPrincipal() instanceof User){
            User user = (User)authentication.getPrincipal();
            BilanceInfoJSON bilanceInfoJSON = new BilanceInfoJSON();
            Account account = accountRepository.findAllByUser(user).get(0);
            if(account==null){
                logger.error("getPrincipalNLastTransfers: Found account is null");
                return null;
            }
            return accountService.getNLastTransfers(n.intValue(), account.getAccountId());

        }else{
            logger.error("getPrincipalNLastTransfers: Principal is not instance of user");
            return null;
        }

    }

}
