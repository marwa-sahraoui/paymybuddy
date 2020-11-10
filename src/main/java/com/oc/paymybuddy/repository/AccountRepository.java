package com.oc.paymybuddy.repository;

import com.oc.paymybuddy.entity.Account;
import com.oc.paymybuddy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.security.acl.Owner;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findAll();

    Account findByOwner_Email(String email);
}
