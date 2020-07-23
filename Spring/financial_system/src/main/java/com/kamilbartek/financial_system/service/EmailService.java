package com.kamilbartek.financial_system.service;


import com.kamilbartek.financial_system.Currency;
import com.kamilbartek.financial_system.jsons.MailFeedback;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.validation.ValidationException;
import java.math.BigDecimal;

@Service
public class EmailService {

    private boolean isSet = false;
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

    public boolean isSet() {
        return isSet;
    }

    public void setSet(boolean set) {
        isSet = set;
    }

    public JavaMailSenderImpl getMailSender() {
        return mailSender;
    }

    public void setMailSender(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }

    public void setConfiguration(String host, Integer port, String username, String password){

        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        isSet = true;
    }
    public void sendFeedback(MailFeedback mailFeedback, BindingResult bindingResult){


        if(bindingResult.hasErrors())
        {
            throw new ValidationException("Feedback is not valid");
        }

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(mailFeedback.getEmail());
        mailMessage.setTo("fake@feedback.com");
        mailMessage.setSubject("New Feedback from " + mailFeedback.getName());
        mailMessage.setText(mailFeedback.getFeedback());

        // Wysyłanie maila
        mailSender.send(mailMessage);

    }

    public boolean passCodeToUsersMail(String code, String mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("odNas@sprawdzenie.org"); // TODO
        mailMessage.setTo(mail);
        mailMessage.setSubject("Verify your transfer");
        mailMessage.setText(code);

        // Wysyłanie maila
        mailSender.send(mailMessage);

        return true;
    }

    public boolean incomingTransferInfo(String receiverMail, String senderName, String senderSurname, BigDecimal amount, String currency) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("odNas@sprawdzenie.org"); // TODO
        mailMessage.setTo(receiverMail);
        mailMessage.setSubject("Incoming transfer");
        mailMessage.setText(senderName+" "+senderSurname+" wants to send you a transfer "+ amount +" "+ currency);

        // Wysyłanie maila
        mailSender.send(mailMessage);

        return true;
    }

    public boolean transferStatus(String senderMail,String receiverName, String receiverSurname, BigDecimal amount, String currency) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("odNas@sprawdzenie.org"); // TODO
        mailMessage.setTo(senderMail);
        mailMessage.setSubject("Transfer passed successfully");
        mailMessage.setText("Your transfer to "+receiverName+" "+receiverSurname+" of  "+amount +" "+ currency+ "has been sent successfully ");

        // Wysyłanie maila
        mailSender.send(mailMessage);

        return true;
    }

}
