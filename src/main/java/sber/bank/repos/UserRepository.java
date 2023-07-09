package sber.bank.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sber.bank.domain.User;

/**
 * Репозиторий для работы с пользователями.
 * Расширяет интерфейс JpaRepository для осуществления операций с базой данных.
 */
public interface UserRepository extends JpaRepository<User, Long> {
}