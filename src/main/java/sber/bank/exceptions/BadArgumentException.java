package sber.bank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение, которое выбрасывается в случае некорректного аргумента.
 * Наследуется от IllegalArgumentException.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadArgumentException extends IllegalArgumentException {

    /**
     * Конструктор с параметром.
     *
     * @param message Сообщение об ошибке.
     */
    public BadArgumentException(String message) {
        super(message);
    }
}

