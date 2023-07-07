package sber.bank.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sber.bank.domain.Account;
import sber.bank.domain.User;

import java.util.List;

public interface AccountRepo extends JpaRepository<Account, Long> {
    List<Account> findByUser(User user);
}
