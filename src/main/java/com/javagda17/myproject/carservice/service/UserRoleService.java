package com.javagda17.myproject.carservice.service;

import com.javagda17.myproject.carservice.model.UserRole;
import com.javagda17.myproject.carservice.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    public UserRole getUserRole(){
        Optional<UserRole>optionalUserRole=userRoleRepository.findByName("ROLE_USER");
        if (optionalUserRole.isPresent()){
            return optionalUserRole.get();
        }
        throw new DataIntegrityViolationException("USER_ROLE should exists in database.");
    }
}
