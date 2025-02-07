package wk.banco.sangue.api.configs.exception;

import org.springframework.http.HttpStatus;

import wk.banco.sangue.api.configs.exception.runtime.WkSangueException;

public class NotFoundException extends WkSangueException {

    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

}
