package sber.bank.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sber.bank.domain.Account;
import sber.bank.domain.Card;

import java.util.List;

/**
 * Репозиторий для работы с банковскими картами.
 * Расширяет интерфейс JpaRepository для осуществления операций с базой данных.
 */
public interface CardRepository extends JpaRepository<Card, Long> {

    /**
     * Возвращает список банковских карт, связанных с указанным банковским счетом.
     *
     * @param account Банковский счет.
     * @return Список банковских карт, связанных с указанным счетом.
     */
    List<Card> findByAccount(Account account);
}