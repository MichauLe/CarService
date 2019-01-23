package com.javagda17.myproject.carservice.controller;

import com.javagda17.myproject.carservice.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IndexController {

    @Autowired
    private AppUserService appUserService;

    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }

    @GetMapping("/")
    public String getIndex(){
        return "index";
    }

    @GetMapping("/register")
    public String getRegisterPage(){
        return "register";
    }

    @PostMapping("/register")
    public String submitRegisterForm(String username,
                                     String password,
                                     String passwordConfirm){

        if (!password.equals(passwordConfirm)){
            return "redirect:/register?error=Password doesnt match";
        }
        boolean result = appUserService.register(username, password);

        if (!result){
            return "redirect:/register?error=Registration error";
        }

        return "redirect:/login";
    }


}
