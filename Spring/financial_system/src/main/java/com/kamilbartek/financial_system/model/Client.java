package com.kamilbartek.financial_system.model;


import javax.persistence.*;
import java.util.Date;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private String username;

    private String password;

    private String name;

    private String surname;

    private String phone_number;

    private String address;

    private String country;

    private String identity_card_number;

    private String date_of_birth;

    private String email_address;

    private String getClientInfo(){
        return ""; 
    }
    @Temporal(TemporalType.DATE)
    Date account_creation_date;

    @OneToOne(mappedBy = "client")
    private Account account;

}
