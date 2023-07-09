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

import java.util.Currency;
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

    private final AccountService accountService;

    /**
     * Конструктор с параметрами.
     *
     * @param userRepository    Репозиторий пользователя.
     * @param accountRepository Репозиторий банковского счёта.
     * @param cardRepository    Репозиторий банковской карты.
     * @param accountService    Сервис банковских счетов.
     */
    @Autowired
    public UserService(UserRepository userRepository, AccountRepository accountRepository, CardRepository cardRepository, AccountService accountService) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.cardRepository = cardRepository;
        this.accountService = accountService;
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
        List<Account> accounts = getAccounts(id);

        for (Account account : accounts) {
            accountService.delete(account.getNumber());
        }

        userRepository.delete(getByPk(id));
    }

    /**
     * Обновляет данные пользователя.
     *
     * @param id         Идентификатор пользователя.
     * @param userDetail Обновленные данные пользователя.
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
     * Возвращает общий баланс пользователя (сумму балансов всех его счетов в рублях).
     *
     * @param id Идентификатор пользователя.
     * @return Общий баланс пользователя в рублях.
     */
    public double getOverallBalance(Long id) {
        List<Account> accounts = getAccounts(id);
        double totalBalance = 0.0;

        for (Account account : accounts) {
            String currencyCode = account.getCurrency();
            double accountBalance = account.getBalance();

            if (!currencyCode.equals("RUB")) {
                Currency accountCurrency = Currency.getInstance(currencyCode);
                double exchangeRate = getExchangeRate(accountCurrency, Currency.getInstance("RUB"));
                accountBalance *= exchangeRate;
            }

            totalBalance += accountBalance;
        }

        return totalBalance;
    }

    /**
     * Возвращает текущий курс обмена между двумя валютами.
     * Курс валют захардкожен. При большом желании можно реализовать конвертер валют, но времени уже не было.
     *
     * @param sourceCurrency Исходная валюта.
     * @param targetCurrency Целевая валюта.
     * @return Курс обмена между исходной и целевой валютами.
     */
    private double getExchangeRate(Currency sourceCurrency, Currency targetCurrency) {
        if (sourceCurrency.getCurrencyCode().equals("USD") && targetCurrency.getCurrencyCode().equals("RUB")) {
            return 90.0;
        }

        if (sourceCurrency.getCurrencyCode().equals("EUR") && targetCurrency.getCurrencyCode().equals("RUB")) {
            return 100.0;
        }

        return 1.0;
    }
}