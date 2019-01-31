package com.javagda17.myproject.carservice.service;

import com.javagda17.myproject.carservice.model.AppUser;
import com.javagda17.myproject.carservice.model.dto.AppUserUpdateRequestDto;
import com.javagda17.myproject.carservice.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Service
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRoleService userRoleService;


    public boolean register(String username, String password) {
        AppUser appUser = new AppUser();
        appUser.setUsername(username);
        appUser.setPassword(bCryptPasswordEncoder.encode(password));
        appUser.getRoles().add(userRoleService.getUserRole());

        try {
            appUserRepository.saveAndFlush(appUser);
        } catch (ConstraintViolationException cve) {
            return false;
        }
        return true;
    }

    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }

    public AppUser getCurrentUser() {
        Optional<AppUser> currentUser = appUserRepository.findByUsername(((org.springframework.security.core.userdetails.User)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        return currentUser.orElse(null);
    }

    public void updateProfil(AppUserUpdateRequestDto dto) {
        Optional<AppUser> user = appUserRepository.findById(dto.getIdUserEdited());
        if (user.isPresent()) {
            AppUser appUser = user.get();
//            if (dto.getUseryearUserEdited()!=null){
            appUser.setUseryear(Long.valueOf(dto.getUseryearUserEdited()));
//            }
//            if (dto.getUsermilageUserEdited()!=null){
            appUser.setUsermilage(Long.valueOf(dto.getUsermilageUserEdited()));
//            }
            appUserRepository.saveAndFlush(appUser);
        }
    }

    public Optional<AppUser> findByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }
}
