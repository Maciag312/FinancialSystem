package com.kamilbartek.financial_system.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Transfer {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private String name;

    private String surname;

    private String phone_number;

    private String address;

    private String country;

    private String identity_card_number;

    private String date_of_birth;

    private String email_address;

    @Temporal(TemporalType.DATE)
    Date account_creation_date;
}
