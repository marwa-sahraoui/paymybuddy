package com.oc.paymybuddy.service;

import com.oc.paymybuddy.entity.Account;
import com.oc.paymybuddy.entity.CreditCard;
import com.oc.paymybuddy.entity.Transaction;
import com.oc.paymybuddy.entity.User;
import com.oc.paymybuddy.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    CreditCardService creditCardService;

    @Autowired
    BankService bankService;

    @Autowired
    UserService userService;

    //constructeur utilisé dans le test de methode send
    public TransactionService(TransactionRepository transactionRepository, AccountService accountService, CreditCardService creditCardService, BankService bankService, UserService userService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.creditCardService = creditCardService;
        this.bankService = bankService;
        this.userService = userService;
    }

    // methode calculate montant envoyé avec le tax
    public BigDecimal calculateAmountWithTax(BigDecimal amountSended) {
        return amountSended.multiply(BigDecimal.valueOf(1.005)) ;
    }

    /*méthode de recharge du compte, on vérifie que l'opération est fesable à travers
    la méthode requestMoney si oui on appel la méthode ajouterArgent*/
    public boolean rechargeCompte(Long creditCardId, BigDecimal amount, Principal user) {

        //creditcard à partir de son id
        CreditCard creditCard = this.creditCardService.findById(creditCardId);
        //piratage
        if (!creditCard.getAccount().getOwner().getEmail().equals(user.getName())) {
            throw new SecurityException("attention tentative de piratage!!");
        }

        boolean request = bankService.requestMoney(creditCard, amount);

        if (request == true) {

            accountService.ajouterArgent(creditCard.getAccount(), amount);
            return true;
        } else {
            throw new IllegalStateException("Operation de recharge non reussie");
        }
    }

    // methode de transfert d'argent d'utilisateur vers son compte bancaire

    public boolean tranfertduComptAppliAuCompteBancaire
    (CreditCard creditCard, BigDecimal amount) {

        boolean request = bankService.requestMoney(creditCard, amount);
        if (request == true) {
            accountService.retirerArgent(creditCard.getAccount(), amount);
            return true;
        } else {
            throw new IllegalStateException("Operation de recharge non reussie");
        }

    }

    public boolean send(Principal currentUser, Long receiverId, BigDecimal amountSended) {
        // Principal.getName retourne un email - car on a defini email comme
        // dans la configuration de l'authentication
        String senderEmail = currentUser.getName();
        User sender = userService.findByEmail(senderEmail);

        User receiver = userService.findById(receiverId);

        return send(sender, receiver, amountSended);//fait appel à la 2eme methode send
    }

    /*
    methode booleenne pour verifier la possibilté d'effectuer le transfert
    d'abord on verifie si le compte sender existe ou pas
    on vérifie que le prix envoyé <au prix existant dans le comte du sender
    on verifie  si le compte receiver existe ou pas
    on fait appel au countService la methode de deduction et celle d ajout du montant
     */
    public boolean send(User sender, User receiver, BigDecimal amountSended) {

        Account accountSender =
                accountService.findAccountByEmail(sender.getEmail());

        if (accountSender == null) {
            throw new IllegalStateException("Compte Sender introuvable");
        }

        BigDecimal totalAmount = calculateAmountWithTax(amountSended);

        if (accountSender.getAmount().compareTo(totalAmount) > 0) {

            Account accountReceiver =
                    accountService.findAccountByEmail(receiver.getEmail());

            if (accountReceiver == null) {
                throw new IllegalStateException("Compte Receiver introuvable");
            }

            accountService.retirerArgent(accountSender, totalAmount);
            accountService.ajouterArgent(accountReceiver, amountSended);

            // transaction avec les  nouvelles valeurs puis sera enregistré dans le repository
            Transaction transaction = new Transaction(
                    amountSended,
                    totalAmount.subtract(amountSended),
                    accountSender,
                    accountReceiver
            );
            transactionRepository.save(transaction);

            return true;
        }
        throw new IllegalStateException("Solde insuffisant");
    }
// toutes les methodes sont privées

}