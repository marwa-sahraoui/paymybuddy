package com.oc.paymybuddy.controller;


import com.oc.paymybuddy.entity.CreditCard;
import com.oc.paymybuddy.entity.User;
import com.oc.paymybuddy.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionRestController {

    @Autowired
    TransactionService transactionService;

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping("/send")
    public boolean send(@RequestBody SendTransaction sendTransaction, Principal principal) {
        return transactionService.send(principal, sendTransaction.getReceiverId(), sendTransaction.getAmountSended());
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping("/recharge")
    public boolean rechargeCompte(@RequestBody Recharge recharge, Principal user) {
        return transactionService.rechargeCompte(recharge.getCreditCard(), recharge.getAmount(), user);
    }
}
