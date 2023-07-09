package sber.bank.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sber.bank.domain.Account;
import sber.bank.domain.Card;
import sber.bank.domain.User;
import sber.bank.exceptions.NotFoundException;
import sber.bank.repos.AccountRepository;
import sber.bank.repos.CardRepository;
import sber.bank.repos.UserRepository;
import sber.bank.service.IService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация сервиса для работы с пользователями банковской системы.
 */
@Service
public class UserService implements IService<User> {
    /**
     * Репозиторий для доступа к данным о пользователях.
     */
    private final UserRepository userRepository;

    /**
     * Репозиторий для доступа к данным о банковских счетах.
     */
    private final AccountRepository accountRepository;

    /**
     * Репозиторий для доступа к данным о банковских картах.
     */
    private final CardRepository cardRepository;

    /**
     * Конструктор с параметрами.
     *
     * @param userRepository    Репозиторий пользователя.
     * @param accountRepository Репозиторий банковского счёта.
     * @param cardRepository    Репозиторий банковской карты.
     */
    @Autowired
    public UserService(UserRepository userRepository, AccountRepository accountRepository, CardRepository cardRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.cardRepository = cardRepository;
    }

    /**
     * Возвращает всех пользователей.
     *
     * @return Итерируемая коллекция пользователей.
     */
    @Override
    public Iterable<User> getAll() {
        return userRepository.findAll();
    }

    /**
     * Возвращает пользователя по его идентификатору.
     *
     * @param id Идентификатор пользователя.
     * @return Пользователь с указанным идентификатором.
     * @throws NotFoundException Если пользователь не найден.
     */
    @Override
    public User getByPk(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Не существует пользователя с ID " + id));
    }

    /**
     * Создает нового пользователя.
     *
     * @param user Данные нового пользователя.
     * @return Созданный пользователь.
     */
    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    /**
     * Удаляет пользователя по его идентификатору.
     *
     * @param id Идентификатор пользователя.
     */
    @Override
    public void delete(Long id) {
        userRepository.delete(getByPk(id));
    }

    /**
     * Обновляет данные пользователя.
     *
     * @param id          Идентификатор пользователя.
     * @param userDetail  Обновленные данные пользователя.
     */
    @Override
    public void update(Long id, User userDetail) {
        User user = getByPk(id);

        user.setName(userDetail.getName());
        user.setBirthdate(userDetail.getBirthdate());
        user.setAddress(userDetail.getAddress());

        userRepository.save(user);
    }

    /**
     * Возвращает список банковских счетов пользователя.
     *
     * @param id Идентификатор пользователя.
     * @return Список банковских счетов пользователя.
     */
    public List<Account> getAccounts(Long id) {
        return accountRepository.findByUser(getByPk(id));
    }

    /**
     * Возвращает список банковских карт пользователя.
     *
     * @param id Идентификатор пользователя.
     * @return Список банковских карт пользователя.
     */
    public List<Card> getCards(Long id) {
        List<Account> userAccounts = getAccounts(id);

        return userAccounts.stream()
                .flatMap(account -> cardRepository.findByAccount(account).stream())
                .collect(Collectors.toList());
    }

    /**
     * Возвращает общий баланс пользователя (сумму балансов всех его счетов).
     *
     * @param id Идентификатор пользователя.
     * @return Общий баланс пользователя.
     */
    public double getOverallBalance(Long id) {
        return getAccounts(id).stream()
                .mapToDouble(Account::getBalance)
                .sum();
    }
}