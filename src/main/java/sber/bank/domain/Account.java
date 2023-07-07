package sber.bank.domain;

import jakarta.persistence.*;

/**
 * Банковский счет.
 */
@Entity
public class Account {
    /**
     * Номер счета.
     */
    @Id
    private Long number;

    /**
     * Владелец.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    /**
     * Валюта.
     */
    private String currency;

    /**
     * Баланс.
     */
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
}
