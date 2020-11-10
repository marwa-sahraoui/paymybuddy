package com.oc.paymybuddy.service;

import com.oc.paymybuddy.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceItegrationTest {

    @Autowired
    UserService userService;
    @Test
    void create() {
        int nombreUsersInitial = userService.findAll().size();

        User userCreated = new User("Jason", "Bourne", "jason@gmail.com", "superPassword", Collections.emptyList());

        User userSaved = userService.create(userCreated);

        //on ajoute un utilisateur jason on verifie qu'on obtient un nombre d'utilisateur = nombre initial +1
        assertEquals(nombreUsersInitial + 1, userService.findAll().size());
        assertTrue(userSaved.getId() != null); //on vérifie que son id n'est pas null

        // nettoyer la base
        userService.delete(userSaved);
    }

}