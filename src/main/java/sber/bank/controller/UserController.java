package sber.bank.controller;

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
     * @throws NotFoundException Если пользователь не найден.
     */
    @GetMapping("{id}/accounts")
    public List<Account> getUserAccounts(@PathVariable Long id) {
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
     * @throws NotFoundException Если пользователь не найден.
     */
    @GetMapping("{id}/cards")
    public List<Card> getUserCards(@PathVariable Long id) {
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
     * @throws NotFoundException Если пользователь не найден.
     */
    @GetMapping("{id}/balance")
    public double getOverallBalance(@PathVariable Long id) {
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
     * @throws NotFoundException Если пользователь не найден.
     */
    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
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
     * @throws NotFoundException Если пользователь не найден.
     */
    @PutMapping("/update/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody User userDetail) {
        // region Проверка входных данных
        Validation.validateUserId(id);
        Validation.validateUser(userDetail);
        // endregion

        userService.update(id, userDetail);
    }
}