package com.kamilbartek.financial_system.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class PendingTransfer {
    @Id

    @GeneratedValue(strategy= GenerationType.AUTO)
    private long transferId;

    private BigDecimal amount;

    private String currency;


    private String code;

    private boolean isSent = false;

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    private Account sender;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account reciever;

    public long getTransferId() {
        return transferId;
    }

    public void setTransferId(long transferId) {
        this.transferId = transferId;
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

    public Date getCreation_time() {
        return creation_time;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCreation_time(Date creation_time) {
        this.creation_time = creation_time;
    }

    @Temporal(TemporalType.TIMESTAMP)
    private Date post_date;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creation_time;



    @Temporal(TemporalType.TIMESTAMP)
    private Date recieve_date;
}
