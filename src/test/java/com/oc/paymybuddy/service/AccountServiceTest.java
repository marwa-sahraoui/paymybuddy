package com.oc.paymybuddy.service;

import com.oc.paymybuddy.entity.Account;
import com.oc.paymybuddy.entity.User;
import com.oc.paymybuddy.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    private AccountService accountService;

    private List<Account> accounts;

    @BeforeEach
    void setUp() {
        User user1 = new User("Jason", "Bourne", "jason@gmail.com", "superPassword", Collections.emptyList());
        Account compte1 = new Account(BigDecimal.valueOf(200L), new Date(), user1);
        compte1.setId(1L);

        User user2 = new User("John", "Smith", "john@gmail.com", "superPassword", Collections.emptyList());
        Account compte2 = new Account(BigDecimal.valueOf(500L), new Date(), user2);
        compte2.setId(2L);

        accounts = new ArrayList<>();
        accounts.add(compte1);
        accounts.add(compte2);

        accountService = new AccountService(accountRepository);
    }

    @Test
    void findAll() {
        when(accountRepository.findAll()).then(x -> accounts);

        List<Account> accounts = accountService.findAll();

        assertThat(accounts.size()).isEqualTo(2);


        long nombreDeComptesAvecUnSoldeSuperieurA100 = accounts.stream()
                .filter(account -> account.getAmount().compareTo(BigDecimal.valueOf(100)) > 0)
                .count();

        assertThat(nombreDeComptesAvecUnSoldeSuperieurA100).isEqualTo(2);

        Optional<Account> compte = accounts.stream()
                .filter(account -> account.getOwner().getEmail().equals("jason@gmail.com"))
                .findFirst();
        assertThat(compte.get().getAmount()).isEqualTo(BigDecimal.valueOf(200L));
    }

    @Test
    void findAccountByEmail() {
        when(accountRepository.findByOwner_Email(anyString())).then(x -> accounts.get(0));

        Account account = accountService.findAccountByEmail("jason@gmail.com");

        assertThat(account.getAmount()).isEqualTo(BigDecimal.valueOf(200L));
        assertThat(account.getOwner().getEmail()).isEqualTo("jason@gmail.com");
    }
//
    @Test
    void retirerArgent() {
        when(accountRepository.getOne(anyLong()))
                .thenAnswer(parameters -> accounts
                        .stream()
                        .filter(account -> account.getId().equals(parameters.getArgument(0)))
                        .findFirst()
                        .get());

        when(accountRepository.save(Mockito.any(Account.class)))
                .then(parameter -> {
                    Account accountToSave = parameter.getArgument(0);

                    accounts = accounts.stream()
                            .filter(compte -> !compte.getId().equals(accountToSave.getId()))
                            .collect(Collectors.toList());

                    accounts.add(accountToSave);

                    return accountToSave;
                });

        accountService.retirerArgent(accounts.get(0), BigDecimal.valueOf(180L));

        Account account = accountService.findById(1L);
        //si on retire 180 du compte1 ayant à la base 200 on va avoir 20 restant
        assertThat(account.getAmount()).isEqualTo(BigDecimal.valueOf(20));
    }

    @Test
    void ajouterArgent() {
        when(accountRepository.getOne(anyLong()))
                .thenAnswer(parameters -> accounts
                        .stream()
                        .filter(account -> account.getId().equals(parameters.getArgument(0)))
                        .findFirst()
                        .get());

        when(accountRepository.save(Mockito.any(Account.class)))
                .then(parameter -> {
                    Account accountToSave = parameter.getArgument(0);

                    accounts = accounts.stream()
                            .filter(compte -> !compte.getId().equals(accountToSave.getId()))
                            .collect(Collectors.toList());

                    accounts.add(accountToSave);

                    return accountToSave;
                });

        accountService.ajouterArgent(accounts.get(0), BigDecimal.valueOf(180L));

        Account account = accountService.findById(1L);
        // si on ajoute 180 au compte1 ayant 200 à la base on aura 380
        assertThat(account.getAmount()).isEqualTo(BigDecimal.valueOf(380));
    }

    @Test
    void findById() {
        when(accountRepository.getOne(anyLong()))
                .thenAnswer(parameters -> accounts
                        .stream()
                        .filter(account -> account.getId().equals(parameters.getArgument(0)))
                        .findFirst()
                        .get());

        Account compte = accountService.findById(2L);

        assertThat(compte.getId()).isEqualTo(2L);
        assertThat(compte.getAmount()).isEqualTo(BigDecimal.valueOf(500));
        assertThat(compte.getOwner().getEmail()).isEqualTo("john@gmail.com");
    }
}