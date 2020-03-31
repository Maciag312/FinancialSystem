package com.kamilbartek.financial_system.service;

import com.kamilbartek.financial_system.model.Client;
import com.kamilbartek.financial_system.repository.ClientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;
    Client client;
    private Client getClientInfo(){
        return clientRepository.findById(client.getClientId());
    }
    public Boolean newClient(String name, String surename, String username,String password, String phone_number,String adress, String country,String identity_card_number, Date date_of_birth, String email_address)
    {
    client = new Client();
    client.getClientId();
    client.setName(name);
    client.setSurname(surename);
    client.setUsername(username);
    client.setPassword(password);
    client.setPhone_number(phone_number);
    client.setAddress(adress);
    client.setEmail_address(email_address);
    client.setCountry(country);
    client.setIdentity_card_number(identity_card_number);
    client.setDate_of_birth(date_of_birth);

    clientRepository.save(client);
        return true;
    }

}
