package sber.bank.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sber.bank.domain.Account;
import sber.bank.domain.Card;

import java.util.List;

public interface CardRepo extends JpaRepository<Card, Long> {
    List<Card> findByAccount(Account account);
}
