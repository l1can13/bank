package sber.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sber.bank.domain.Account;
import sber.bank.exceptions.NotFoundException;
import sber.bank.repos.AccountRepository;

/**
 * Контроллер, отвечающий за обработку запросов, связанных со счетами пользователей.
 */
@RestController
@RequestMapping("/account")
public class AccountController {
    /**
     * Репозиторий банковского счета.
     */
    @Autowired
    private AccountRepository accountRepository;

    /**
     * Создание нового счета пользователя.
     *
     * @param account Данные нового счета.
     * @return Сообщение об успешном создании.
     */
    @PostMapping("/create")
    public String createAccount(@RequestBody Account account) {
        accountRepository.save(account);

        return "Successfully created: " + account;
    }

    /**
     * Удаление счета пользователя по его номеру.
     *
     * @param number Номер счета.
     * @return Сообщение об успешном удалении.
     * @throws NotFoundException Если счет с указанным номером не найден.
     */
    @PostMapping("/delete/{number}")
    public String deleteAccount(@PathVariable Long number) {
        Account account = accountRepository.findById(number)
                .orElseThrow(() -> new NotFoundException("Account does not exist with number: " + number));

        accountRepository.delete(account);
        return "Successfully deleted: " + account;
    }

    /**
     * Обновление данных счета пользователя по его номеру.
     *
     * @param number        Номер счета.
     * @param accountDetail Обновленные данные счета.
     * @return Сообщение об успешном обновлении.
     * @throws NotFoundException Если счет с указанным номером не найден.
     */
    @PutMapping("/update/{number}")
    public String updateAccount(@PathVariable Long number, @RequestBody Account accountDetail) {
        Account account = accountRepository.findById(number)
                .orElseThrow(() -> new NotFoundException("Account does not exist with number: " + number));

        account.setUser(accountDetail.getUser());
        account.setCurrency(accountDetail.getCurrency());
        account.setBalance(accountDetail.getBalance());

        accountRepository.save(account);

        return "Successfully updated!";
    }
}