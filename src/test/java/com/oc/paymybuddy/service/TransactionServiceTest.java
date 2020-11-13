package com.oc.paymybuddy.service;

import com.oc.paymybuddy.entity.Account;
import com.oc.paymybuddy.entity.CreditCard;
import com.oc.paymybuddy.entity.User;
import com.oc.paymybuddy.repository.AccountRepository;
import com.oc.paymybuddy.repository.TransactionRepository;
import com.oc.paymybuddy.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountService accountService;

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    CreditCardService creditCardService;

    @Mock
    BankService bankService;

    @Mock
    UserService userService;

    private List<User> users;
    private List<Account> comptes;

    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        User user1 = new User("Jason", "Bourne", "jason@gmail.com", "superPassword", Collections.emptyList());
        Account compte1 = new Account(BigDecimal.valueOf(200L), new Date(), user1);
        compte1.setId(1L);

        User user2 = new User("John", "Smith", "john@gmail.com", "superPassword", Collections.emptyList());
        Account compte2 = new Account(BigDecimal.valueOf(500L), new Date(), user2);
        compte2.setId(2L);

        users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        comptes = new ArrayList<>();
        comptes.add(compte1);
        comptes.add(compte2);

        accountService = new AccountService(accountRepository);

        transactionService = new TransactionService(transactionRepository, accountService, creditCardService, bankService, userService);
    }

    @Test
    void send() {
        //mocker find by email : si je donne any email je reçois en resultat le premier utilisateur owner du compte
        when(accountService.findAccountByEmail(anyString()))
                .then(parameter ->
                        comptes.stream()
                                .filter(compte -> compte.getOwner().getEmail().equals(parameter.getArgument(0)))//condition
                                .findFirst()
                                .get()
                );
        //mocker la méthode save: si je donne n'importe quel compte, j'efface ce compte puis je l'ajoute à la liste des compte
        //principe de l'update pour ne pas avoir 2 fois le mm compte avant et apres une telle opération
        when(accountRepository.save(any(Account.class)))
                .then(parameter -> {

                    Account accountToSave = parameter.getArgument(0);

                    comptes.removeIf(account -> account.getId().equals(accountToSave.getId()));

                    comptes.add(accountToSave);

                    return accountToSave;
                });

        boolean sent = transactionService.send(users.get(0), users.get(1), BigDecimal.valueOf(100));

        assertTrue(sent);
        /*
       user1 possédant 200 si il va transférer 100 donc on va déduire 100.5 , il lui reste alors 99.5
       user2 posssédant 500 il reçoit 100 donc il aura 600 dans son compte
         */
        assertEquals(0, comptes.get(0).getAmount().compareTo(BigDecimal.valueOf(99.5)));
        assertEquals(0, comptes.get(1).getAmount().compareTo(BigDecimal.valueOf(600)));
    }

    @Test
    /*ce test permet de vérifier le calcul * 1.005
    expected 0 c'est la différence entre ce qui est valeur estimée de la méthode calculateAmountWithTax
    et la valeur proprement calculée
    */
    void calculateAmountWithTax() {
        BigDecimal amountWithTax = transactionService.calculateAmountWithTax(BigDecimal.valueOf(100));
        assertEquals(0, BigDecimal.valueOf(100.5).compareTo(amountWithTax));

        amountWithTax = transactionService.calculateAmountWithTax(BigDecimal.valueOf(1000));
        assertEquals(0, BigDecimal.valueOf(1005).compareTo(amountWithTax));

        amountWithTax = transactionService.calculateAmountWithTax(BigDecimal.valueOf(0));
        assertEquals(0, BigDecimal.ZERO.compareTo(amountWithTax));
    }

    @Test
    void rechargeCompte() {

        Account compte = comptes.get(0);

        CreditCard creditCard = new CreditCard("Hello World", compte);
        creditCard.setId(1L);

        when(creditCardService.findById(any())).then(x -> creditCard);
        when(bankService.requestMoney(any(), any())).then(x -> true);

       Principal user = () -> compte.getOwner().getEmail();

        boolean done = transactionService.rechargeCompte(creditCard.getId(), BigDecimal.valueOf(300), user);

        assertTrue(done);
        //compte ayant 200 au départ si on ajoute 300 on aura dans le compte 500
        assertEquals(BigDecimal.valueOf(500), compte.getAmount());
    }


    @Test
    void tranfertduComptAppliAuCompteBancaire() {

        Account compte = comptes.get(0);

        CreditCard creditCard = new CreditCard("Hello World", compte);
        creditCard.setId(1L);


        when(bankService.requestMoney(any(), any())).then(x -> true);

        //compte ayant 200 au départ si on déduit 100 il rest 100
        boolean done = transactionService.tranfertduComptAppliAuCompteBancaire(creditCard, BigDecimal.valueOf(100));

        assertTrue(done);

        assertEquals(BigDecimal.valueOf(100), compte.getAmount());
    }


}