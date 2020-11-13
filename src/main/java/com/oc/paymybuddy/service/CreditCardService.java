package com.oc.paymybuddy.service;

import com.oc.paymybuddy.entity.CreditCard;
import com.oc.paymybuddy.repository.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditCardService {
    @Autowired
    CreditCardRepository creditCardRepository;

    //methode qui retourne une carte credit à partir de son id
    public CreditCard findById(Long creditCardId) {
        return creditCardRepository.getOne(creditCardId);
    }
}
