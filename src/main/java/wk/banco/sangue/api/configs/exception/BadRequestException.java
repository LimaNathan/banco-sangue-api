package wk.banco.sangue.api.configs.exception;

import org.springframework.http.HttpStatus;

import wk.banco.sangue.api.configs.exception.runtime.WkSangueException;

public class BadRequestException extends WkSangueException {

    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

}
