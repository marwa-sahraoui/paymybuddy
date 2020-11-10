package com.oc.paymybuddy.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name ="Transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    private BigDecimal tax ;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "sender")
    private Account sender;

    @ManyToOne
    @JoinColumn(name = "receiver")
    private Account receiver;

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

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public Account getSender() {
        return sender;
    }

    public void setSender(Account sender) {
        this.sender = sender;
    }

    public Account getReceiver() {
        return receiver;
    }

    public void setReceiver(Account receiver) {
        this.receiver = receiver;
    }

    public Transaction() {
    }

    public Transaction(BigDecimal amount, BigDecimal tax, Account sender, Account receiver) {
        this.amount = amount;
        this.tax = tax;
        this.sender = sender;
        this.receiver = receiver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        Transaction that = (Transaction) o;
        return amount.equals(that.amount) &&
                tax.equals(that.tax) &&
                date.equals(that.date) &&
                sender.equals(that.sender) &&
                receiver.equals(that.receiver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, tax, sender, receiver);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "amount=" + amount +
                ", tax=" + tax +
                ", sender=" + sender +
                ", receiver=" + receiver +
                '}';
    }
}
