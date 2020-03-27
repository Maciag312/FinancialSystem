package com.kamilbartek.financial_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication

public class FinancialSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinancialSystemApplication.class, args);
    }

}
