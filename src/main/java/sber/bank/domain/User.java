package sber.bank.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

/**
 * Пользователь банковской системы.
 */
@Entity
public class User {
    /**
     * Уникальный идентификатор пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * Имя пользователя.
     */
    private String name;

    /**
     * Дата рождения пользователя.
     */
    private Date birthdate;

    /**
     * Адрес пользователя.
     */
    private String address;

    /**
     * Конструктор по умолчанию.
     */
    public User() {

    }

    /**
     * Конструктор с параметрами.
     *
     * @param name      Имя пользователя.
     * @param birthdate Дата рождения пользователя.
     * @param address   Адрес пользователя.
     */
    public User(String name, Date birthdate, String address) {
        this.name = name;
        this.birthdate = birthdate;
        this.address = address;
    }

    /**
     * Получение идентификатора пользователя.
     *
     * @return Идентификатор пользователя.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Изменение идентификатора пользователя.
     *
     * @param id Новый идентификатор пользователя.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Получение имени пользователя.
     *
     * @return Имя пользователя.
     */
    public String getName() {
        return name;
    }

    /**
     * Изменение имени пользователя.
     *
     * @param name Новое имя пользователя.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Получение даты рождения пользователя.
     *
     * @return Дата рождения пользователя.
     */
    public Date getBirthdate() {
        return birthdate;
    }

    /**
     * Изменение даты рождения пользователя.
     *
     * @param birthdate Новая дата рождения пользователя.
     */
    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    /**
     * Получение адреса пользователя.
     *
     * @return Адрес пользователя.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Изменение адреса пользователя.
     *
     * @param address Новый адрес пользователя.
     */
    public void setAddress(String address) {
        this.address = address;
    }
}
