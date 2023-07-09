package sber.bank.domain;

import jakarta.persistence.*;

import java.util.Date;

/**
 * Банковская карта.
 */
@Entity
public class Card {
    /**
     * Номер карты.
     */
    @Id
    @Column(name="\"number\"")
    private Long number;

    /**
     * Банковский счет, связанный с картой.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_number")
    private Account account;

    /**
     * Дата окончания срока действия карты.
     */
    private Date expirationDate;

    /**
     * CVV-код карты.
     */
    private Integer cvv;

    /**
     * Конструктор по умолчанию.
     */
    public Card() {

    }

    /**
     * Конструктор с параметрами.
     *
     * @param account        Банковский счет, связанный с картой.
     * @param expirationDate Дата окончания срока действия карты.
     * @param cvv            CVV-код карты.
     */
    public Card(Account account, Date expirationDate, Integer cvv) {
        this.account = account;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
    }

    /**
     * Конструктор копирования.
     * @param card Объект Card.
     */
    public Card(Card card) {
        this.number = card.getNumber();
        this.account = card.getAccount();
        this.expirationDate = card.getExpirationDate();
        this.cvv = card.getCvv();
    }

    /**
     * Получение номера карты.
     *
     * @return Номер карты.
     */
    public Long getNumber() {
        return number;
    }

    /**
     * Изменение номера карты.
     *
     * @param number Новый номер карты.
     */
    public void setNumber(Long number) {
        this.number = number;
    }

    /**
     * Получение банковского счета, связанного с картой.
     *
     * @return Банковский счет.
     */
    public Account getAccount() {
        return account;
    }

    /**
     * Изменение банковского счета, связанного с картой.
     *
     * @param account Новый банковский счет.
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * Получение даты окончания срока действия карты.
     *
     * @return Дата окончания срока действия карты.
     */
    public Date getExpirationDate() {
        return expirationDate;
    }

    /**
     * Изменение даты окончания срока действия карты.
     *
     * @param expirationDate Новая дата окончания срока действия карты.
     */
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * Получение CVV-кода карты.
     *
     * @return CVV-код карты.
     */
    public Integer getCvv() {
        return cvv;
    }

    /**
     * Изменение CVV-кода карты.
     *
     * @param cvv Новый CVV-код карты.
     */
    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }
}
