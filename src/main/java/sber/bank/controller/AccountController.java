package sber.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sber.bank.domain.Account;
import sber.bank.exceptions.BadArgumentException;
import sber.bank.exceptions.NotFoundException;
import sber.bank.service.implementation.AccountService;
import sber.bank.validation.Validation;

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
     * @throws BadArgumentException Если номер счета некорректен.
     */
    @PostMapping("/create")
    public boolean createAccount(@RequestBody Account account) {
        // region Проверка входных данных
        Validation.validateAccount(account);
        // endregion

        return accountService.create(account).equals(account);
    }

    /**
     * Удаляет счет пользователя по его номеру.
     *
     * @param number Номер счета.
     * @throws BadArgumentException Если номер счета некорректен.
     * @throws NotFoundException Если счет с указанным номером не найден.
     */
    @PostMapping("/delete/{number}")
    public void deleteAccount(@PathVariable Long number) {
        // region Проверка входных данных
        Validation.validateAccountNumber(number);
        // endregion

        accountService.delete(number);
    }

    /**
     * Обновляет данные счета пользователя по его номеру.
     *
     * @param number        Номер счета.
     * @param accountDetail Обновленные данные счета.
     * @throws BadArgumentException Если номер счета некорректен.
     * @throws NotFoundException Если счет с указанным номером не найден.
     */
    @PutMapping("/update/{number}")
    public void updateAccount(@PathVariable Long number, @RequestBody Account accountDetail) {
        // region Проверка входных данных
        Validation.validateAccountNumber(number);
        Validation.validateAccount(accountDetail);
        // endregion

        accountService.update(number, accountDetail);
    }
}