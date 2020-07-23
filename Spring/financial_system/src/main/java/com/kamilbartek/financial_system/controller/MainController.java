package com.kamilbartek.financial_system.controller;


import com.kamilbartek.financial_system.jsons.*;
import com.kamilbartek.financial_system.model.Account;
import com.kamilbartek.financial_system.model.User;
import com.kamilbartek.financial_system.repository.AccountRepository;
import com.kamilbartek.financial_system.repository.UserRepository;
import com.kamilbartek.financial_system.service.AccountService;
import com.kamilbartek.financial_system.service.InternalExchangeOffice;
import com.kamilbartek.financial_system.service.TransferService;
import com.kamilbartek.financial_system.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class MainController {


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

    @Autowired
    InternalExchangeOffice internalExchangeOffice;


    @GetMapping("/greeting")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name, Authentication authentication) {
        return "Greetings " + authentication.getName() + name + " !";
    }


    @PostMapping("/createTransferAndPassCode")
    public boolean createTransferAndPassCode(@RequestBody TransferJSON transferJSON) {
        Account account_from = accountRepository.findById(transferJSON.sender_account_id).orElse(null);
        Account account_to = accountRepository.findById(transferJSON.reciever_account_id).orElse(null);
        if (account_from == null || account_to == null) return false;
        return transferService.createTransferAndPassCode(account_from, account_to, BigDecimal.valueOf(transferJSON.amount), transferJSON.currency);
    }



    @PostMapping(value = "/createClient", consumes = "application/json")
    public boolean createClient(@RequestBody ClientJSON clientJSON) {
        return userSerivce.createUser(clientJSON.username, clientJSON.password, clientJSON.name, clientJSON.surname, clientJSON.phone_number, clientJSON.address, clientJSON.country, clientJSON.identity_card_number, clientJSON.date_of_birth, clientJSON.email_address);
    }

    @PostMapping("/createAccount/{user_id}/{currency}")
    public boolean createAccount(@PathVariable Long user_id, @PathVariable String currency) {
        User client = userRepository.findById(user_id).orElse(null);
        if (client == null) return false;
        return accountService.createAccount(client, currency);
    }


    @GetMapping("/getUsers")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/getAccounts")
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @GetMapping("/getNLastTransfers/{n}/{account_id}")
    public List<TransferJSON> getNLastTransfers(@PathVariable Integer n, @PathVariable Long account_id) {
        return accountService.getNLastTransfers(n.intValue(), account_id);
    }

    @GetMapping("/isAccountAvailable/{account_id}")
    public boolean isAccountAvailable(@PathVariable Long account_id, Authentication authentication) {
        if (authentication.isAuthenticated())
            return accountRepository.findByUniqueId(account_id).isPresent();
        return false;
    }

    @GetMapping("/sufficientFunds/{amount}/{account_id}")
    public boolean sufficientFunds(@PathVariable BigDecimal amount, @PathVariable long account_id, Authentication authentication) {
        if (authentication.isAuthenticated()) {
            if (authentication.getPrincipal() instanceof User) {
                List<Account> acs = accountService.getAccountsByUser((User) authentication.getPrincipal());
                boolean found = false;
                for (int i = 0; i < acs.size(); i++) {
                    if (acs.get(i).getUniqueId() == account_id) {
                        found = true;
                        break;
                    }
                }
                if (!found) return false;
                else return accountRepository.findByUniqueId(account_id).get().getBilance().subtract(amount).doubleValue() >= 0.0;
            }
        }
        return false;
    }

    @GetMapping("/createTransferAndPassCode/{amount}/{from_account_id}/{to_account_id}")
    public boolean transfer(@PathVariable BigDecimal amount, @PathVariable Long from_account_id, @PathVariable Long to_account_id, Authentication authentication){
        Account from = accountRepository.findByUniqueId(from_account_id).get();
        Account to = accountRepository.findByUniqueId(to_account_id).get();
        if(from==null||to==null) return false;
        return transferService.createTransferAndPassCode(from,to,amount,from.getCurrency());
    }
    @GetMapping("/validateCodeAndSendTransfer/{code}/{from_account_id}/{to_account_id}")
    public boolean validateCodeAndSendTransfer(@PathVariable String code, @PathVariable Long from_account_id, @PathVariable Long to_account_id, Authentication authentication){
        Account from = accountRepository.findByUniqueId(from_account_id).get();
        Account to = accountRepository.findByUniqueId(to_account_id).get();
        if(from==null||to==null) return false;
        return transferService.validateCodeAndSendTransfer(code, from,to);
    }


    @GetMapping("/getPrincipalAccounts")
    public List<AccountJSON> getPrincipalAccounts(Authentication authentication){
        if(authentication.isAuthenticated()){
            if(authentication.getPrincipal() instanceof User){
                return accountService.getUserAccounts((User)authentication.getPrincipal());
            }
        }
        return null;
    }


    Logger logger = LoggerFactory.getLogger(MainController.class);


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

    @GetMapping("/getCurrencies/{currency}")
    public List<CurrencyJSON> getCurrencies(@PathVariable String currency)
    {
        try {
            internalExchangeOffice.updateRates(currency);
        } catch(IOException i){
            System.out.println("Currencies exceptions" + i);
        }
        List<CurrencyJSON> CurrencyJSONS = new ArrayList<>();
        for (Map.Entry<String,BigDecimal> cur: internalExchangeOffice.getRates().getRates().entrySet()) {

            CurrencyJSON currencyJSON = new CurrencyJSON();

            currencyJSON.name = cur.getKey();
            currencyJSON.value = cur.getValue().doubleValue();

            CurrencyJSONS.add(currencyJSON);

        }
        return CurrencyJSONS;
    }

}
