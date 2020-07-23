package com.kamilbartek.financial_system.controller;

import com.kamilbartek.financial_system.jsons.MailFeedback;
import com.kamilbartek.financial_system.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mailFeedback")
public class EmailController {

    @Autowired
    EmailService emailService;


    @PostMapping
    public void sendFeedback(@RequestBody MailFeedback mailFeedback, BindingResult bindingResult){
        if(!emailService.isSet()){
            emailService.setConfiguration("smtp.mailtrap.io", 2525,"f157b2f6f1e8a7", "cf00b375b28b1a");
        }
        emailService.sendFeedback(mailFeedback,bindingResult);



    }
}
