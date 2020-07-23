package com.kamilbartek.financial_system.repository;

import com.kamilbartek.financial_system.model.Account;
import com.kamilbartek.financial_system.model.PendingTransfer;
import com.kamilbartek.financial_system.model.Transfer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface  PendingTransferRepository extends CrudRepository<PendingTransfer, Long> {
    List<PendingTransfer> findAllBySender(Account account);
    List<PendingTransfer> findAllByReciever(Account account);
    Optional<PendingTransfer> findByCodeAndSender(String code, Account from);
}
