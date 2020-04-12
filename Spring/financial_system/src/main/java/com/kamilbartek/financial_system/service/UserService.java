package com.kamilbartek.financial_system.service;

import com.kamilbartek.financial_system.enums.Role;
import com.kamilbartek.financial_system.model.User;

import com.kamilbartek.financial_system.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean createUser(String username, String password, String name, String surname, String phone_number, String address, String country, String identity_card_number, Date date_of_birth, String email_address){
        User user = new User(username,passwordEncoder.encode(password),name,surname,phone_number,address,country,identity_card_number,date_of_birth,email_address);
        user.grantAuthority(Role.USER);
        Logger logger = LoggerFactory.getLogger(Service.class);
        logger.info(user.toString());
        userRepository.save(user);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(s);

        if (user.isPresent()){
            return user.get();
        }else{
            throw new UsernameNotFoundException(String.format("Username[%s] not found"));
        }
    }
}