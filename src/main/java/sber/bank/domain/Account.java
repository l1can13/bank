package sber.bank.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.Objects;

/**
 * Банковский счет.
 */
@Entity
@Schema(description = "Банковский счет")
public class Account {
    /**
     * Номер счета.
     */
    @Id
    @Column(name = "\"number\"")
    @Schema(description = "Номер счета", example = "1234567890123456")
    private Long number;

    /**
     * Владелец.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @Schema(description = "Владелец счета")
    private User user;

    /**
     * Валюта.
     */
    @Schema(description = "Валюта", example = "RUB, USD, EUR")
    private String currency;

    /**
     * Баланс.
     */
    @Schema(description = "Баланс", example = "1000.0")
    private Double balance;

    /**
     * Конструктор по умолчанию.
     */
    public Account() {

    }

    /**
     * Конструктор с параметрами.
     *
     * @param user     Владелец.
     * @param currency Валюта.
     * @param balance  Баланс.
     */
    public Account(User user, String currency, Double balance) {
        this.user = user;
        this.currency = currency;
        this.balance = balance;
    }

    /**
     * Конструктор с параметрами.
     *
     * @param number   Номер счёта.
     * @param user     Владелец.
     * @param currency Валюта.
     * @param balance  Баланс.
     */
    public Account(Long number, User user, String currency, Double balance) {
        this.number = number;
        this.user = user;
        this.currency = currency;
        this.balance = balance;
    }

    /**
     * Конструктор копирования.
     *
     * @param account Объект Account.
     */
    public Account(Account account) {
        this.number = account.getNumber();
        this.user = account.getUser();
        this.currency = account.getCurrency();
        this.balance = account.getBalance();
    }

    /**
     * Получение номера счета.
     *
     * @return Номер счета.
     */
    public Long getNumber() {
        return number;
    }

    /**
     * Изменение номера счета.
     *
     * @param number Новый номер счета.
     */
    public void setNumber(Long number) {
        this.number = number;
    }

    /**
     * Получение владельца счета.
     *
     * @return Владелец счета.
     */
    public User getUser() {
        return user;
    }

    /**
     * Изменение владельца счета.
     *
     * @param user Новый владелец счета.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Получение валюты счета.
     *
     * @return Валюта счета.
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Изменение валюты счета.
     *
     * @param currency Новая валюта счета.
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * Получение баланса счета.
     *
     * @return Баланс счета.
     */
    public Double getBalance() {
        return balance;
    }

    /**
     * Изменение баланса счета.
     *
     * @param balance Новый баланс счета.
     */
    public void setBalance(Double balance) {
        this.balance = balance;
    }

    /**
     * Проверяет, является ли указанный объект равным данному банковскому счету.
     *
     * @param obj Объект для сравнения.
     * @return true, если указанный объект равен данному банковскому счету, иначе false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Account account = (Account) obj;

        return number.equals(account.number)
                && user.equals(account.user)
                && currency.equals(account.currency)
                && balance.equals(account.balance);
    }

    /**
     * Возвращает хеш-код текущего объекта.
     *
     * @return Хеш-код объекта.
     */
    @Override
    public int hashCode() {
        return Objects.hash(number, user, currency, balance);
    }

    /**
     * Возвращает строковое представление данного банковского счета.
     *
     * @return Строковое представление банковского счета.
     */
    @Override
    public String toString() {
        return String.format("Account{number=%d, user=%s, currency='%s', balance=%.2f}",
                number, user, currency, balance);
    }
}
