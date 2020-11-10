package com.oc.paymybuddy.service;


import com.oc.paymybuddy.entity.Account;
import com.oc.paymybuddy.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    //methode pour retourner tous les comptes
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    //methode qui retourne un compte à travers l'email de l
    public Account findAccountByEmail(String email) {
        return accountRepository.findByOwner_Email(email);
    }

    //methode permet de deducter un montant du compte sender
    public void retirerArgent(Account account, BigDecimal amount) {
        account.setAmount(account.getAmount().subtract(amount));

        accountRepository.save(account);
    }

    //methode pour ajouter un montant au compte receiver
    public void ajouterArgent(Account account, BigDecimal amount) {
        account.setAmount(account.getAmount().add(amount));

        accountRepository.save(account);
    }

    public Account findById(Long id) {
        return accountRepository.getOne(id);
    }


}
