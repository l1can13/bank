package sber.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sber.bank.domain.Account;
import sber.bank.domain.Card;
import sber.bank.domain.User;
import sber.bank.exceptions.NotFoundException;
import sber.bank.repos.AccountRepo;
import sber.bank.repos.CardRepo;
import sber.bank.repos.UserRepo;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private CardRepo cardRepo;

    @GetMapping("all_users")
    public Iterable<User> getAllUsers() {
        return userRepo.findAll();
    }

    @GetMapping("{id}/accounts")
    public List<Account> getUserAccounts(@PathVariable Long id) {
        if (userRepo.findById(id).isPresent()) {
            return accountRepo.findByUser(userRepo.findById(id).get());
        } else {
            throw new NotFoundException();
        }
    }

    @GetMapping("{id}/cards")
    public List<Card> getCards(@PathVariable Long id) {
        List<Account> accounts;

        if (userRepo.findById(id).isPresent()) {
            accounts = accountRepo.findByUser(userRepo.findById(id).get());
        }
        else {
            throw new NotFoundException();
        }

        List<Card> resultList = new ArrayList<Card>();

        for (Account account : accounts) {
            resultList.addAll(cardRepo.findByAccount(account));
        }

        return resultList;
    }

    @GetMapping("{id}/balance")
    public double getOverallBalance(@PathVariable Long id) {
        List<Account> userAccounts = getUserAccounts(id);
        double result = 0.0;

        for(Account account : userAccounts) {
            result += account.getBalance();
        }

        return result;
    }
}
