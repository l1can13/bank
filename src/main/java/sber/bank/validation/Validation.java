package sber.bank.validation;

import sber.bank.domain.Account;
import sber.bank.domain.Card;
import sber.bank.domain.User;
import sber.bank.exceptions.BadArgumentException;

/**
 * Валидация.
 */
public class Validation {
    /**
     * Проверяет корректность данных счета.
     *
     * @param account Данные счета.
     * @throws BadArgumentException Если данные счета некорректны.
     */
    public static void validateAccount(Account account) {
        if (account == null) {
            throw new BadArgumentException("Данные счета не могут быть пустыми");
        }

        if (account.getUser() == null) {
            throw new BadArgumentException("Не указан владелец счета");
        }

        if (account.getCurrency() == null || account.getCurrency().isEmpty()) {
            throw new BadArgumentException("Не указана валюта счета");
        }
    }

    /**
     * Проверяет корректность номера счета.
     *
     * @param number Номер счета.
     * @throws BadArgumentException Если номер счета некорректен.
     */
    public static void validateAccountNumber(Long number) {
        if (number == null || String.valueOf(number).length() < 15) {
            throw new BadArgumentException("Некорректный номер счета");
        }
    }

    /**
     * Проверяет корректность данных карты.
     *
     * @param card Карта для проверки.
     * @throws BadArgumentException Если данные карты некорректны.
     */
    public static void validateCard(Card card) {
        if (card == null) {
            throw new BadArgumentException("Данные карты не могут быть пустыми");
        }

        if (card.getAccount() == null) {
            throw new BadArgumentException("Не указан счет, к которому привязана карта");
        }

        if (card.getExpirationDate() == null) {
            throw new BadArgumentException("Не указан срок действия карты");
        }

        if (card.getCvv() == null) {
            throw new BadArgumentException("Не указан CVV код карты");
        }
    }

    /**
     * Проверяет корректность номера карты.
     *
     * @param number Номер карты для проверки.
     * @throws BadArgumentException Если номер карты некорректен.
     */
    public static void validateCardNumber(Long number) {
        if (number == null
            || String.valueOf(number).length() != 13
            || String.valueOf(number).length() != 15
            || String.valueOf(number).length() != 16
            || String.valueOf(number).length() != 18
            || String.valueOf(number).length() != 19) {
            throw new BadArgumentException("Некорректный номер карты");
        }
    }

    /**
     * Проверяет корректность данных пользователя.
     *
     * @param user Пользователь для проверки.
     * @throws BadArgumentException Если данные пользователя некорректны.
     */
    public static void validateUser(User user) {
        if (user == null) {
            throw new BadArgumentException("Данные пользователя не могут быть пустыми");
        }

        if (user.getName() == null || user.getName().isEmpty()) {
            throw new BadArgumentException("Не указано имя пользователя");
        }

        if (user.getBirthdate() == null) {
            throw new BadArgumentException("Не указана дата рождения пользователя");
        }

        if (user.getAddress() == null || user.getAddress().isEmpty()) {
            throw new BadArgumentException("Не указан адрес проживания пользователя");
        }
    }

    /**
     * Проверяет корректность идентификационного номера пользователя.
     *
     * @param id Идентификационный номер пользователя для проверки.
     * @throws BadArgumentException Если идентификационный номер пользователя некорректен.
     */
    public static void validateUserId(Long id) {
        if (id == null || id < 0) {
            throw new BadArgumentException("Некорректный идентификационный номер пользователя");
        }
    }
}
