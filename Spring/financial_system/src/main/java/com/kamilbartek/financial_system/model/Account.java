package com.kamilbartek.financial_system.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
public class Account {
        @Id
        @GeneratedValue(strategy= GenerationType.AUTO)
        private long id;

        private BigDecimal bilance;
        private String currency;
        @Temporal(TemporalType.DATE)
        private Date account_creation_date;

        @OneToMany(mappedBy="reciever")
        private List<Transfer> incoming_transfers;

        @OneToMany(mappedBy="sender")
        private List<Transfer> sent_transfers;


        @OneToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "user_id", referencedColumnName = "id")
        private User user;

        public void setId(long id) {
            this.id = id;
        }
        public long getId() {
        return id;
    }

        public BigDecimal getBilance() {
                return bilance;
        }

        public void setBilance(BigDecimal bilance) {
                this.bilance = bilance;
        }

        public List<Transfer> getIncoming_transfers() {
            return incoming_transfers;
        }

        public void setIncoming_transfers(List<Transfer> incoming_transfers) {
            this.incoming_transfers = incoming_transfers;
        }

        public List<Transfer> getSent_transfers() {
            return sent_transfers;
        }

        public void setSent_transfers(List<Transfer> sent_transfers) {
            this.sent_transfers = sent_transfers;
        }

    public User getUser() {
                    return user;
            }

        public void setUser(User user) {
                this.user = user;
        }

        public Date getAccount_creation_date() {
                return account_creation_date;
        }

        public void setAccount_creation_date(Date account_creation_date) {
                this.account_creation_date = account_creation_date;
        }

        public String getCurrency() {
        return currency;
    }

        public void setCurrency(String currency) {
            this.currency = currency;
        }
}
