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

        public Client getClient() {
                return client;
        }

        public void setClient(Client client) {
                this.client = client;
        }

        public Date getAccount_creation_date() {
                return account_creation_date;
        }

        public void setAccount_creation_date(Date account_creation_date) {
                this.account_creation_date = account_creation_date;
        }
}
