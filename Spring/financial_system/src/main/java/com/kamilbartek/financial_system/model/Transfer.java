package com.kamilbartek.financial_system.model;

import com.kamilbartek.financial_system.Cash;

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

    @ManyToOne
    private Account sender;

    @ManyToOne
    private Account reciever;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Account getReciever() {
        return reciever;
    }

    public void setReciever(Account reciever) {
        this.reciever = reciever;
    }

    public Account getSender() {
        return sender;
    }

    public void setSender(Account sender) {
        this.sender = sender;
    }

    public Date getPost_date() {
        return post_date;
    }

    public void setPost_date(Date post_date) {
        this.post_date = post_date;
    }

    public Date getRecieve_date() {
        return recieve_date;
    }

    public void setRecieve_date(Date recieve_date) {
        this.recieve_date = recieve_date;
    }

    @Temporal(TemporalType.DATE)
    private Date post_date;

    @Temporal(TemporalType.DATE)
    private Date recieve_date;
}
