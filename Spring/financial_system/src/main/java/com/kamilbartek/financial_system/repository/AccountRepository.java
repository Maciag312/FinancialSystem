package com.kamilbartek.financial_system.repository;

import java.util.List;

import com.kamilbartek.financial_system.model.Account;
import org.springframework.data.repository.CrudRepository;


public interface AccountRepository extends CrudRepository<Account, Long> {
    List<Account> findByClientId(String lastName);
    Account findById(long id);
}