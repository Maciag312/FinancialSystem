package com.kamilbartek.financial_system.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Transfer {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private BigDecimal amount;

    private String currency;

    private Account sender;

    private Account reciever;


    @Temporal(TemporalType.DATE)
    private Date post_date;


    @Temporal(TemporalType.DATE)
    private Date recieve_date;
}
