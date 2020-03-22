package com.kamilbartek.financial_system.repository;

import java.util.List;

import com.kamilbartek.financial_system.model.Client;
import org.springframework.data.repository.CrudRepository;


public interface ClientRepository extends CrudRepository<Client, Long> {
    List<Client> findByNameAndSurname(String name, String surname);
    Client findById(long id);
}