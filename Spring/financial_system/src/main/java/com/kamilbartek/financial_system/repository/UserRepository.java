package com.kamilbartek.financial_system.repository;

import java.util.List;
import java.util.Optional;

import com.kamilbartek.financial_system.model.Account;
import com.kamilbartek.financial_system.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findByNameAndSurname(String name, String surname);

    Optional<User> findByUsername(String username);

    List<User> findAll();

    User findById(long id);
}
