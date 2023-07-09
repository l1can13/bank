package sber.bank.controller;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sber.bank.domain.Account;
import sber.bank.domain.Card;
import sber.bank.domain.User;
import sber.bank.exceptions.NotFoundException;
import sber.bank.repos.AccountRepo;
import sber.bank.repos.CardRepo;
import sber.bank.repos.UserRepo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        return accountRepo.findByUser(
                userRepo.findById(id)
                        .orElseThrow(() -> new NotFoundException("User not exist with id: " + id)));
    }

    @GetMapping("{id}/cards")
    public List<Card> getCards(@PathVariable Long id) {
        List<Account> userAccounts = getUserAccounts(id);

        List<Card> resultList = new ArrayList<Card>();

        for (Account account : userAccounts) {
            resultList.addAll(cardRepo.findByAccount(account));
        }

        return resultList;
    }

    @GetMapping("{id}/balance")
    public double getOverallBalance(@PathVariable Long id) {
        List<Account> userAccounts = getUserAccounts(id);
        double result = 0.0;

        for (Account account : userAccounts) {
            result += account.getBalance();
        }

        return result;
    }

    @PostMapping("/create")
    public String createUser(@RequestBody User user) {
        userRepo.save(user);

        return "Successfully created: " + user;
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("User not exist with id: " + id));

        userRepo.delete(user);
        return "Successfully deleted: " + user;
    }

    @PutMapping("/update/{id}")
    public String updateUser(@PathVariable Long id, @RequestBody User userDetail) {
        User user = userRepo.findById(id).orElseThrow(() -> new NotFoundException("User not exist with id: " + id));

        user.setName(userDetail.getName());
        user.setBirthdate(userDetail.getBirthdate());
        user.setAddress(userDetail.getAddress());

        userRepo.save(user);

        return "Successfully updated!";
    }
}
