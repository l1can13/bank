package sber.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sber.bank.domain.Account;
import sber.bank.domain.Card;
import sber.bank.exceptions.NotFoundException;
import sber.bank.repos.AccountRepo;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountRepo accountRepo;

    @PostMapping("/create")
    public String createAccount(@RequestBody Account account) {
        accountRepo.save(account);

        return "Successfully created: " + account;
    }

    @PostMapping("/delete/{number}")
    public String deleteAccount(@PathVariable Long number) {
        Account account = accountRepo.findById(number)
                .orElseThrow(() -> new NotFoundException("Account not exist with number: " + number));

        accountRepo.delete(account);
        return "Successfully deleted: " + account;
    }

    @PutMapping("/update/{number}")
    public String updateAccount(@PathVariable Long number, @RequestBody Account accountDetail) {
        Account account = accountRepo.findById(number)
                .orElseThrow(() -> new NotFoundException("Account not exist with number: " + number));

        account.setUser(accountDetail.getUser());
        account.setCurrency(accountDetail.getCurrency());
        account.setBalance(accountDetail.getBalance());

        accountRepo.save(account);

        return "Successfully updated!";
    }
}
