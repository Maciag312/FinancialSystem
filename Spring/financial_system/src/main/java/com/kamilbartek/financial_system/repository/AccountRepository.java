package com.kamilbartek.financial_system.repository;

import java.util.List;
import java.util.Optional;

import com.kamilbartek.financial_system.model.Account;
import com.kamilbartek.financial_system.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    Account findById(long id);

    Optional<Account> findByUniqueId(Long unique_id);

    List<Account> findAllByUser(User user);
    List<Account> findAll();
}