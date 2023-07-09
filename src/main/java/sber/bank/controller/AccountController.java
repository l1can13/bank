package sber.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sber.bank.domain.Account;
import sber.bank.exceptions.NotFoundException;
import sber.bank.service.implementation.AccountService;

/**
 * Контроллер, отвечающий за обработку запросов, связанных со счетами пользователей.
 */
@RestController
@RequestMapping("api/account")
public class AccountController {
    /**
     * Сервис для работы с банковскими счетами.
     */
    @Autowired
    private AccountService accountService;

    /**
     * Создает новый счет пользователя.
     *
     * @param account Данные нового счета.
     * @return true, если счет успешно создан и совпадает с переданными данными, иначе false.
     */
    @PostMapping("/create")
    public boolean createAccount(@RequestBody Account account) {
        return accountService.create(account).equals(account);
    }

    /**
     * Удаляет счет пользователя по его номеру.
     *
     * @param number Номер счета.
     * @throws NotFoundException Если счет с указанным номером не найден.
     */
    @PostMapping("/delete/{number}")
    public void deleteAccount(@PathVariable Long number) {
        accountService.delete(number);
    }

    /**
     * Обновляет данные счета пользователя по его номеру.
     *
     * @param number        Номер счета.
     * @param accountDetail Обновленные данные счета.
     * @throws NotFoundException Если счет с указанным номером не найден.
     */
    @PutMapping("/update/{number}")
    public void updateAccount(@PathVariable Long number, @RequestBody Account accountDetail) {
        accountService.update(number, accountDetail);
    }
}