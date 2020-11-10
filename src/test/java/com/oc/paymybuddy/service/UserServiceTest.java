package com.oc.paymybuddy.service;

import com.oc.paymybuddy.entity.Account;
import com.oc.paymybuddy.entity.User;
import com.oc.paymybuddy.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    UserService userService;

    BCryptPasswordEncoder passwordEncoder;
    List<User> users;


    @BeforeEach
    void setUp() {
        users = new ArrayList<>();
        passwordEncoder = new BCryptPasswordEncoder();
        userService = new UserService(userRepository, passwordEncoder);
    }


    @Test
    void addContact() {

       User james = new User();

        when(userRepository.findByEmail(anyString())).thenAnswer(parameters -> james);
        //jason.getContact().size ().isEqualTo(0)
        User jason = new User("Jason", "Bourne", "jason@gmail.com", "superPassword", new ArrayList<>());
        //on va ajouter james à partir de son email mocké à la liste de contact de jason
        userService.addContact(jason, "james@gmail.com");
        //on vérifie que le size de contact de jason devient 1
        assertThat(jason.getContacts().size()).isEqualTo(1);
    }


    @Test
    void findByEmail() {

        User user = new User("Jason", "Bourne", "jason@gmail.com", "superPassword", new ArrayList<>());

        when(userRepository.findByEmail(anyString())).thenAnswer(parameters -> user);

        User userByEmail = userService.findByEmail("jason@gmail.com");

        assertThat(userByEmail.getEmail().equals("jason@gmail.com"));
    }

    @Test
    void findById() {
        User user = new User("Jason", "Bourne", "jason@gmail.com", "superPassword", new ArrayList<>());
        user.setId(1L);

        when(userRepository.getOne(anyLong())).thenAnswer(parameters -> user);

        User userById = userService.findById(1L);

        assertThat(userById.getId().equals(1L));
    }
}