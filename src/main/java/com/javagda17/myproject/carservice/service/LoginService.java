package com.javagda17.myproject.carservice.service;

import com.javagda17.myproject.carservice.model.AppUser;
import com.javagda17.myproject.carservice.model.UserRole;
import com.javagda17.myproject.carservice.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class LoginService implements UserDetailsService {
    @Autowired
    private AppUserRepository appUserRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Optional<AppUser> appUserOptional = appUserRepository.findByUsername(username);
            if(appUserOptional.isPresent()){
                AppUser appUser = appUserOptional.get();
                // todo: uprawnienia

                Set<String> grantedAuthoritySet = new HashSet<>();
                for (UserRole role : appUser.getRoles()){
                    grantedAuthoritySet.add(role.getName().replace("ROLE_",""));
                }
                String[] authorities = grantedAuthoritySet.toArray(new String[grantedAuthoritySet.size()]);
                // za porównanie hasła odpowiada spring
                return User.builder()
                        .username(appUser.getUsername())
                        .password(appUser.getPassword())
                        .disabled(false)
                        .roles(authorities)
                        .build();
            }
            throw new UsernameNotFoundException("Username: " +username + " could not be found.");
    }
}
