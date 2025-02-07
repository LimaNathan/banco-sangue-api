package wk.banco.sangue.api.configs.exception.runtime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import wk.banco.sangue.api.domain.dtos.ErrorResponseDTO;

public class WkSangueException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final HttpStatus status;

    public WkSangueException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public ResponseEntity<ErrorResponseDTO> getMessageError() {

        return ResponseEntity.status(status)//
                .body(
                        ErrorResponseDTO.builder()
                                .message(getMessage())
                                .code(status.value())
                                .key(status.getReasonPhrase())
                                .build());
    }

    public HttpStatus getStatus() {
        return status;
    }
}
