package com.kamilbartek.financial_system.repository;

import java.util.List;
import java.util.Optional;

import com.kamilbartek.financial_system.model.Account;
import com.kamilbartek.financial_system.model.Transfer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository extends CrudRepository<Transfer, Long> {
   List<Transfer> findAllBySender(Account account);
   List<Transfer> findAllByReciever(Account account);
}