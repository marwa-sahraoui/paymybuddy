package com.oc.paymybuddy.entity;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Credit_Card")
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_Card")
    private String nameCard;

    @ManyToOne
    @JoinColumn(name = "associate_account")
    private Account associate_account;

    public CreditCard() {
        //
    }

    //constructeur pour la methode de test de recharge
    public CreditCard(String nameCard, Account associate_account) {
        this.nameCard = nameCard;
        this.associate_account = associate_account;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameCard() {
        return nameCard;
    }

    public void setNameCard(String nameCard) {
        this.nameCard = nameCard;
    }

    public Account getAccount() {
        return associate_account;
    }

    public void setAccount(Account account) {
        this.associate_account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCard that = (CreditCard) o;
        return nameCard.equals(that.nameCard) &&
                associate_account.equals(that.associate_account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameCard, associate_account);
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "nameCard='" + nameCard + '\'' +
                ", account=" + associate_account +
                '}';
    }
}
