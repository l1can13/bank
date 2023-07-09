package sber.bank.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Банковские счета", description = "Методы для работы с банковскими счетами")
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
    @Operation(summary = "Создать новый счет пользователя", description = "Создает новый счет пользователя на основе предоставленных данных.")
    @ApiResponse(responseCode = "200", description = "Счет успешно создан")
    @ApiResponse(responseCode = "400", description = "Некорректные данные счета")
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
     * @throws NotFoundException    Если счет с указанным номером не найден.
     */
    @Operation(summary = "Удалить счет пользователя", description = "Удаляет счет пользователя по его номеру.")
    @ApiResponse(responseCode = "204", description = "Счет успешно удален")
    @ApiResponse(responseCode = "400", description = "Некорректный номер счета")
    @ApiResponse(responseCode = "404", description = "Счет не найден")
    @DeleteMapping("/delete/{number}")
    public void deleteAccount(@Parameter(description = "Номер счета", example = "1234567890")
                              @PathVariable Long number) {
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
     * @throws NotFoundException    Если счет с указанным номером не найден.
     */
    @Operation(summary = "Обновить данные счета пользователя", description = "Обновляет данные счета пользователя по его номеру.")
    @ApiResponse(responseCode = "204", description = "Данные счета успешно обновлены")
    @ApiResponse(responseCode = "400", description = "Некорректные данные счета")
    @ApiResponse(responseCode = "404", description = "Счет не найден")
    @PutMapping("/update/{number}")
    public void updateAccount(@Parameter(description = "Номер счета", example = "1234567890") @PathVariable Long number,
                              @RequestBody Account accountDetail) {
        // region Проверка входных данных
        Validation.validateAccountNumber(number);
        Validation.validateAccount(accountDetail);
        // endregion

        accountService.update(number, accountDetail);
    }
}