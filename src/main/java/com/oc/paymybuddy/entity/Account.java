package com.oc.paymybuddy.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "Account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "owner")
    private User owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Account() {
    }

    public Account(Long id) {
        this.id = id;
    }

    public Account(BigDecimal amount, Date date, User owner) {
        this.amount = amount;
        this.date = date;
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return amount.equals(account.amount) &&
                date.equals(account.date) &&
                owner.equals(account.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, date, owner);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", amount=" + amount +
                ", date=" + date +
                ", owner=" + owner +
                '}';
    }
}
