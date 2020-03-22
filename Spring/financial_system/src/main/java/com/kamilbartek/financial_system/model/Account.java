package com.kamilbartek.financial_system.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private BigDecimal bilance;

    @Temporal(TemporalType.DATE)
    Date account_creation_date;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;
}
