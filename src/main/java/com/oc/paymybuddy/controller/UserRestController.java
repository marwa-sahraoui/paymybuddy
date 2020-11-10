package com.oc.paymybuddy.controller;

import com.oc.paymybuddy.entity.User;
import com.oc.paymybuddy.repository.UserRepository;
import com.oc.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserRestController {

    @Autowired
    UserService userService;

    @Secured("ROLE_ADMIN")
    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }



    @PostMapping("/testCreate")
    public User create(@RequestBody User user) {
        return userService.create(user);

    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/exists")
    public boolean exists(@RequestParam String email) {
        return userService.userExists(email);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/contacts/{id}")
    public List<User> getUserContactsByUserID(@PathVariable Long id) {
        return userService.getUserContactsByUserID(id);
    }


}
