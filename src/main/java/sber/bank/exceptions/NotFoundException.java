package sber.bank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение, которое выбрасывается в случае, если ресурс не найден.
 * Наследуется от RuntimeException.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    /**
     * Конструктор с параметром.
     *
     * @param message Сообщение об ошибке.
     */
    public NotFoundException(String message) {
        super(message);
    }
}
