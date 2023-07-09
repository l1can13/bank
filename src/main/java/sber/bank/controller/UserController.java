package sber.bank.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import sber.bank.domain.Account;
import sber.bank.domain.Card;
import sber.bank.domain.User;
import sber.bank.exceptions.BadArgumentException;
import sber.bank.exceptions.NotFoundException;
import sber.bank.service.implementation.UserService;
import sber.bank.validation.Validation;

import java.util.List;

/**
 * Контроллер, отвечающий за обработку запросов, связанных с пользователями.
 */
@RestController
@RequestMapping(value = "api/user")
@Tag(name = "Пользователи", description = "Методы для работы с пользователями")
public class UserController {
    /**
     * Сервис для работы с пользователями.
     */
    private final UserService userService;

    /**
     * Конструктор с параметрами.
     *
     * @param userService Сервис для работы с пользователями.
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Получает список всех пользователей.
     *
     * @return Итерируемый объект с пользователями.
     */
    @Operation(summary = "Получить список всех пользователей", description = "Возвращает список всех зарегистрированных пользователей.")
    @ApiResponse(responseCode = "200", description = "Список пользователей",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class))))
    @GetMapping("all-users")
    public Iterable<User> getAllUsers() {
        return userService.getAll();
    }

    /**
     * Получает список счетов пользователя по его идентификатору.
     *
     * @param id Идентификатор пользователя.
     * @return Список счетов пользователя.
     * @throws BadArgumentException Если идентификационный номер пользователя некорректен.
     * @throws NotFoundException    Если пользователь не найден.
     */
    @Operation(summary = "Получить список счетов пользователя", description = "Возвращает список счетов пользователя по его идентификатору.")
    @ApiResponse(responseCode = "200", description = "Список счетов пользователя",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Account.class))))
    @ApiResponse(responseCode = "400", description = "Некорректный идентификатор пользователя")
    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    @GetMapping("{id}/accounts")
    public List<Account> getUserAccounts(@Parameter(description = "Идентификатор пользователя", example = "1") @PathVariable Long id) {
        // region Проверка входных данных
        Validation.validateUserId(id);
        // endregion

        return userService.getAccounts(id);
    }

    /**
     * Получает список карт пользователя по его идентификатору.
     *
     * @param id Идентификатор пользователя.
     * @return Список карт пользователя.
     * @throws BadArgumentException Если идентификационный номер пользователя некорректен.
     * @throws NotFoundException    Если пользователь не найден.
     */
    @Operation(summary = "Получить список карт пользователя", description = "Возвращает список карт пользователя по его идентификатору.")
    @ApiResponse(responseCode = "200", description = "Список карт пользователя",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Card.class))))
    @ApiResponse(responseCode = "400", description = "Некорректный идентификатор пользователя")
    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    @GetMapping("{id}/cards")
    public List<Card> getUserCards(@Parameter(description = "Идентификатор пользователя", example = "1") @PathVariable Long id) {
        // region Проверка входных данных
        Validation.validateUserId(id);
        // endregion

        return userService.getCards(id);
    }

    /**
     * Получает общий баланс пользователя по его идентификатору.
     *
     * @param id Идентификатор пользователя.
     * @return Общий баланс пользователя.
     * @throws BadArgumentException Если идентификационный номер пользователя некорректен.
     * @throws NotFoundException    Если пользователь не найден.
     */
    @Operation(summary = "Получить общий баланс пользователя", description = "Возвращает общий баланс пользователя по его идентификатору.")
    @ApiResponse(responseCode = "200", description = "Общий баланс пользователя")
    @ApiResponse(responseCode = "400", description = "Некорректный идентификатор пользователя")
    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    @GetMapping("{id}/balance")
    public double getOverallBalance(@Parameter(description = "Идентификатор пользователя", example = "1") @PathVariable Long id) {
        // region Проверка входных данных
        Validation.validateUserId(id);
        // endregion

        return userService.getOverallBalance(id);
    }

    /**
     * Создает нового пользователя.
     *
     * @param user Данные нового пользователя.
     * @return true, если пользователь успешно создан и совпадает с переданными данными, иначе false.
     * @throws BadArgumentException Если данные пользователя некорректны.
     */
    @Operation(summary = "Создать нового пользователя", description = "Создает нового пользователя на основе предоставленных данных.")
    @ApiResponse(responseCode = "200", description = "Пользователь успешно создан")
    @ApiResponse(responseCode = "400", description = "Некорректные данные пользователя")
    @PostMapping("/create")
    public boolean createUser(@RequestBody User user) {
        // region Проверка входных данных
        Validation.validateUser(user);
        // endregion

        return userService.create(user).equals(user);
    }

    /**
     * Удаляет пользователя по его идентификатору.
     *
     * @param id Идентификатор пользователя.
     * @throws BadArgumentException Если идентификационный номер пользователя некорректен.
     * @throws NotFoundException    Если пользователь не найден.
     */
    @Operation(summary = "Удалить пользователя", description = "Удаляет пользователя по его идентификатору.")
    @ApiResponse(responseCode = "204", description = "Пользователь успешно удален")
    @ApiResponse(responseCode = "400", description = "Некорректный идентификатор пользователя")
    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    @DeleteMapping("/delete/{id}")
    public void deleteUser(@Parameter(description = "Идентификатор пользователя", example = "1") @PathVariable Long id) {
        // region Проверка входных данных
        Validation.validateUserId(id);
        // endregion

        userService.delete(id);
    }

    /**
     * Обновляет данные пользователя по его идентификатору.
     *
     * @param id         Идентификатор пользователя.
     * @param userDetail Обновленные данные пользователя.
     * @throws BadArgumentException Если данные пользователя некорректны.
     * @throws NotFoundException    Если пользователь не найден.
     */
    @Operation(summary = "Обновить данные пользователя", description = "Обновляет данные пользователя по его идентификатору.")
    @ApiResponse(responseCode = "204", description = "Данные пользователя успешно обновлены")
    @ApiResponse(responseCode = "400", description = "Некорректные данные пользователя")
    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    @PutMapping("/update/{id}")
    public void updateUser(@Parameter(description = "Идентификатор пользователя", example = "1") @PathVariable Long id,
                           @RequestBody User userDetail) {
        // region Проверка входных данных
        Validation.validateUserId(id);
        Validation.validateUser(userDetail);
        // endregion

        userService.update(id, userDetail);
    }
}