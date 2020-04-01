package com.kamilbartek.financial_system.repository;

import java.util.List;

import com.kamilbartek.financial_system.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    Account findById(long id);
}