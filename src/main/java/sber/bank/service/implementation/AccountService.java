package sber.bank.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sber.bank.domain.Account;
import sber.bank.exceptions.NotFoundException;
import sber.bank.repos.AccountRepository;
import sber.bank.service.IService;

/**
 * Реализация сервиса для работы с банковскими счетами.
 */
@Service
public class AccountService implements IService<Account> {
    private final AccountRepository accountRepository;

    /**
     * Конструктор с параметрами.
     *
     * @param accountRepository Репозиторий банковских счетов.
     */
    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Получает список всех банковских счетов.
     *
     * @return Итерируемый объект с банковскими счетами.
     */
    @Override
    public Iterable<Account> getAll() {
        return accountRepository.findAll();
    }

    /**
     * Получает банковский счет по его номеру.
     *
     * @param number Номер банковского счета.
     * @return Банковский счет с указанным номером.
     * @throws NotFoundException Если банковский счет с указанным номером не найден.
     */
    @Override
    public Account getByPk(Long number) {
        return accountRepository.findById(number)
                .orElseThrow(() -> new NotFoundException("Не существует банковского счета с номером: " + number));
    }

    /**
     * Создает новый банковский счет.
     *
     * @param account Данные нового банковского счета.
     * @return Созданный банковский счет.
     */
    @Override
    public Account create(Account account) {
        return accountRepository.save(account);
    }

    /**
     * Удаляет банковский счет по его номеру.
     *
     * @param number Номер банковского счета.
     * @throws NotFoundException Если банковский счет с указанным номером не найден.
     */
    @Override
    public void delete(Long number) {
        accountRepository.delete(getByPk(number));
    }

    /**
     * Обновляет данные банковского счета по его номеру.
     *
     * @param number        Номер банковского счета.
     * @param accountDetail Обновленные данные банковского счета.
     * @throws NotFoundException Если банковский счет с указанным номером не найден.
     */
    @Override
    public void update(Long number, Account accountDetail) {
        Account account = getByPk(number);

        account.setUser(accountDetail.getUser());
        account.setCurrency(accountDetail.getCurrency());
        account.setBalance(accountDetail.getBalance());

        accountRepository.save(account);
    }
}