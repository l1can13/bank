package sber.bank.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

/**
 * Банковская карта.
 */
@Entity
@Schema(description = "Банковская карта")
public class Card {
    /**
     * Номер карты.
     */
    @Id
    @Column(name = "\"number\"")
    @Schema(description = "Номер карты", example = "1234567890123456")
    private Long number;

    /**
     * Банковский счет, связанный с картой.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_number")
    @Schema(description = "Банковский счет, связанный с картой")
    private Account account;

    /**
     * Дата окончания срока действия карты.
     */
    @Schema(description = "Дата окончания срока действия карты")
    private Date expirationDate;

    /**
     * CVV-код карты.
     */
    @Schema(description = "CVV-код карты", example = "123")
    private Integer cvv;

    /**
     * Конструктор по умолчанию.
     */
    public Card() {

    }

    /**
     * Конструктор с параметрами.
     *
     * @param number         Номер карты.
     * @param account        Банковский счет, связанный с картой.
     * @param expirationDate Дата окончания срока действия карты.
     * @param cvv            CVV-код карты.
     */
    public Card(Long number, Account account, Date expirationDate, Integer cvv) {
        this.number = number;
        this.account = account;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
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
     *
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

    /**
     * Проверяет, является ли указанный объект равным текущему объекту.
     *
     * @param obj Объект для сравнения.
     * @return true, если объекты равны, в противном случае - false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Card card = (Card) obj;

        return number.equals(card.number)
                && account.equals(card.account)
                && expirationDate.equals(card.expirationDate)
                && cvv.equals(card.cvv);
    }

    /**
     * Возвращает хеш-код текущего объекта.
     *
     * @return Хеш-код объекта.
     */
    @Override
    public int hashCode() {
        return Objects.hash(number, account, expirationDate, cvv);
    }

    /**
     * Возвращает строковое представление объекта Card.
     *
     * @return Строковое представление объекта Card.
     */
    @Override
    public String toString() {
        return String.format("Card{number=%d, account=%s, expirationDate=%s, cvv=%d}",
                number, account, expirationDate, cvv);
    }
}
