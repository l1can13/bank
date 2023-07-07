package sber.bank.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sber.bank.domain.User;

public interface UserRepo extends JpaRepository<User, Long> {

}
