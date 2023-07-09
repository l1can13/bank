package sber.bank.domain;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

/**
 * Пользователь банковской системы.
 */
@Entity
@Table(name = "\"user\"")
public class User {
    /**
     * Уникальный идентификатор пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Имя пользователя.
     */
    @Column(name = "\"name\"")
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
     * Конструктор копирования.
     *
     * @param user Объект User.
     */
    public User(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.birthdate = user.getBirthdate();
        this.address = user.getAddress();
    }

    /**
     * Получение идентификатора пользователя.
     *
     * @return Идентификатор пользователя.
     */
    public Long getId() {
        return id;
    }

    /**
     * Изменение идентификатора пользователя.
     *
     * @param id Новый идентификатор пользователя.
     */
    public void setId(Long id) {
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

    /**
     * Проверяет, является ли указанный объект равным данному пользователю.
     *
     * @param obj Объект для сравнения.
     * @return true, если указанный объект равен данному пользователю, иначе false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        User user = (User) obj;

        return id.equals(user.id)
                && name.equals(user.name)
                && birthdate.equals(user.birthdate)
                && address.equals(user.address);
    }

    /**
     * Возвращает хеш-код текущего объекта.
     *
     * @return Хеш-код объекта.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthdate, address);
    }

    /**
     * Преобразует данного пользователя в строку.
     *
     * @return Строковое представление пользователя.
     */
    @Override
    public String toString() {
        return String.format("User{id=%d, name='%s', birthdate=%s, address='%s'}",
                id, name, birthdate, address);
    }
}
