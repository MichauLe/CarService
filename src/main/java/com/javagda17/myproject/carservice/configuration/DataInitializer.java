package com.javagda17.myproject.carservice.configuration;

import com.javagda17.myproject.carservice.model.AppUser;
import com.javagda17.myproject.carservice.model.UserRole;
import com.javagda17.myproject.carservice.repository.AppUserRepository;
import com.javagda17.myproject.carservice.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        checkAndLoadRoles();
        checkAndLoadUsers();
    }

    private void checkAndLoadUsers(){

        if (!checkUser("admin")){
            createUser("admin","admin","ROLE_USER","ROLE_ADMIN");
        }
        if (!checkUser("user")){
            createUser("user","user","ROLE_USER");
        }

        if (!checkUser("ispy")){
            createUser("ispy","user","ROLE_USER","ROLE_SPY");
        }
    }

    private void createUser(String username,String password, String... roles){

        Set<UserRole> userRoles = new HashSet<>();
        for (String role : roles){
            userRoles.add(findRole(role));
        }

        AppUser appUser = AppUser.builder()
                .username(username)
                .password(bCryptPasswordEncoder.encode(password))
                .roles(userRoles)
                .build();

        appUserRepository.saveAndFlush(appUser);
    }

    private UserRole findRole(String role) {
        Optional<UserRole> userRoleOptional = userRoleRepository.findByName(role);
        if (userRoleOptional.isPresent()){
            UserRole userRole = userRoleOptional.get();
            return userRole;
        }
        throw  new DataIntegrityViolationException("User role does not exist. "+
        "Try fixing user role called: "+role+" in Your data initializer.");
    }

    private boolean checkUser(String username){
        return appUserRepository.findByUsername(username).isPresent();
    }

    private void checkAndLoadRoles(){
        if (!checkRole("ROLE_USER")){
            createRole("ROLE_USER");
        }
        if (!checkRole("ROLE_ADMIN")){
            createRole("ROLE_ADMIN");
        }

        if (!checkRole("ROLE_SPY")){
            createRole("ROLE_SPY");
        }
    }

    private void createRole(String role){
        UserRole createdRole = new UserRole(null, role);
        userRoleRepository.saveAndFlush(createdRole);
    }

    private boolean checkRole(String role){
        return userRoleRepository.findByName(role).isPresent();
    }
}
