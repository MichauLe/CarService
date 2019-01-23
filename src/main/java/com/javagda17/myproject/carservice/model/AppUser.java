package com.javagda17.myproject.carservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Size(min = 4)
    @Column(unique = true)
    private String username;
    @Size(min = 4)
    private String password;

    private String name;

    private String surname;
    @Email
    private String email;
    private String phoneNumber;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<UserRole> roles=new HashSet<>();



}
