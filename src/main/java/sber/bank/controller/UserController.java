package sber.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sber.bank.domain.Account;
import sber.bank.domain.Card;
import sber.bank.domain.User;
import sber.bank.exceptions.NotFoundException;
import sber.bank.repos.AccountRepository;
import sber.bank.repos.CardRepository;
import sber.bank.repos.UserRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер, отвечающий за обработку запросов, связанных с пользователями.
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {
    /**
     * Репозиторий пользователя.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Репозиторий банковского счета.
     */
    @Autowired
    private AccountRepository accountRepository;

    /**
     * Репозиторий банковской карты.
     */
    @Autowired
    private CardRepository cardRepository;

    /**
     * Получение списка всех пользователей.
     *
     * @return Итерируемый объект с пользователями.
     */
    @GetMapping("all_users")
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Получение списка счетов пользователя по его идентификатору.
     *
     * @param id Идентификатор пользователя.
     * @return Список счетов пользователя.
     * @throws NotFoundException Если пользователь с указанным идентификатором не найден.
     */
    @GetMapping("{id}/accounts")
    public List<Account> getUserAccounts(@PathVariable Long id) {
        return accountRepository.findByUser(
                userRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("User not exist with id: " + id)));
    }

    /**
     * Получение списка карт пользователя по его идентификатору.
     *
     * @param id Идентификатор пользователя.
     * @return Список карт пользователя.
     * @throws NotFoundException Если пользователь с указанным идентификатором не найден.
     */
    @GetMapping("{id}/cards")
    public List<Card> getCards(@PathVariable Long id) {
        List<Account> userAccounts = getUserAccounts(id);

        List<Card> resultList = new ArrayList<Card>();

        for (Account account : userAccounts) {
            resultList.addAll(cardRepository.findByAccount(account));
        }

        return resultList;
    }

    /**
     * Получение общего баланса пользователя по его идентификатору.
     *
     * @param id Идентификатор пользователя.
     * @return Общий баланс пользователя.
     * @throws NotFoundException Если пользователь с указанным идентификатором не найден.
     */
    @GetMapping("{id}/balance")
    public double getOverallBalance(@PathVariable Long id) {
        List<Account> userAccounts = getUserAccounts(id);
        double result = 0.0;

        for (Account account : userAccounts) {
            result += account.getBalance();
        }

        return result;
    }

    /**
     * Создание нового пользователя.
     *
     * @param user Данные нового пользователя.
     * @return Сообщение об успешном создании.
     */
    @PostMapping("/create")
    public String createUser(@RequestBody User user) {
        userRepository.save(user);

        return "Successfully created: " + user;
    }

    /**
     * Удаление пользователя по его идентификатору.
     *
     * @param id Идентификатор пользователя.
     * @return Сообщение об успешном удалении.
     * @throws NotFoundException Если пользователь с указанным идентификатором не найден.
     */
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not exist with id: " + id));

        userRepository.delete(user);
        return "Successfully deleted: " + user;
    }

    /**
     * Обновление данных пользователя по его идентификатору.
     *
     * @param id          Идентификатор пользователя.
     * @param userDetail  Обновленные данные пользователя.
     * @return Сообщение об успешном обновлении.
     * @throws NotFoundException Если пользователь с указанным идентификатором не найден.
     */
    @PutMapping("/update/{id}")
    public String updateUser(@PathVariable Long id, @RequestBody User userDetail) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not exist with id: " + id));

        user.setName(userDetail.getName());
        user.setBirthdate(userDetail.getBirthdate());
        user.setAddress(userDetail.getAddress());

        userRepository.save(user);

        return "Successfully updated!";
    }
}