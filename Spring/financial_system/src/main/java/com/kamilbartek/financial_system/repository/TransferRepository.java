package com.kamilbartek.financial_system.repository;

import java.util.List;

import com.kamilbartek.financial_system.model.Transfer;
import org.springframework.data.repository.CrudRepository;


public interface TransferRepository extends CrudRepository<Transfer, Long> {
    List<Transfer> findBySenderId(String lastName);
    Transfer findById(long id);
}