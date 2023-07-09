package sber.bank.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sber.bank.domain.Account;
import sber.bank.domain.User;

import java.util.List;

/**
 * Репозиторий для работы с банковскими счетами.
 * Расширяет интерфейс JpaRepository для осуществления операций с базой данных.
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * Возвращает список банковских счетов, принадлежащих указанному пользователю.
     *
     * @param user Пользователь, владеющий счетами.
     * @return Список банковских счетов пользователя.
     */
    List<Account> findByUser(User user);
}
