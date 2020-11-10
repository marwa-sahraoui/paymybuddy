package com.oc.paymybuddy.service;

import com.oc.paymybuddy.entity.CreditCard;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service

public class BankService {

    // Demande du montant pour le compte de l'application
    // l'argent sera versée pour PayMyBuddy depuis la carte bancaire du client
    public boolean requestMoney(CreditCard creditCard, BigDecimal amount) {
        return true;
    }
}
