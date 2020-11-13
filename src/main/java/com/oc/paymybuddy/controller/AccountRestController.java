package com.oc.paymybuddy.controller;

import com.oc.paymybuddy.entity.Account;
import com.oc.paymybuddy.entity.User;
import com.oc.paymybuddy.service.AccountService;
import com.oc.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountRestController {

    @Autowired
    AccountService accountService;

    @Secured("ROLE_ADMIN")
    @GetMapping
    public List<Account> findAll() {
        return accountService.findAll();
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/email")
    public Account findByOwner_Email(@RequestParam String adresse) {
        return accountService.findAccountByEmail(adresse);
    }
}

