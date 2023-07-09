package sber.bank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadArgumentException extends IllegalArgumentException {
    public BadArgumentException(String message) {
        super(message);
    }
}
