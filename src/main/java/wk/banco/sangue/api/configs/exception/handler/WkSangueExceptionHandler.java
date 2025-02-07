
package wk.banco.sangue.api.configs.exception.handler;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import wk.banco.sangue.api.configs.exception.BadRequestException;
import wk.banco.sangue.api.configs.exception.NotFoundException;
import wk.banco.sangue.api.configs.exception.runtime.WkSangueException;
import wk.banco.sangue.api.domain.dtos.ErrorResponseDTO;

@RestControllerAdvice
public class WkSangueExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(WkSangueException.class)
    protected ResponseEntity<ErrorResponseDTO> handlePinkSecretException(WkSangueException exception) {
        logger.error("Erro: {}", exception);
        return exception.getMessageError();
    }

    @SuppressWarnings("null")
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> errors = getErrors(ex);
        ErrorResponseDTO errorResponse = getErrorResponse(ex, HttpStatus.valueOf(status.value()), errors);
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<ErrorResponseDTO> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String message = extractDetailMessage(ex.getMostSpecificCause().getMessage());
        return new ResponseEntity<>(

                ErrorResponseDTO.builder()
                        .message(message)
                        .code(HttpStatus.CONFLICT.value())
                        .key(ex.getMostSpecificCause().getMessage())
                        .build(),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponseDTO> handleAccessDeniedException(AccessDeniedException ex) {
        return new ResponseEntity<>(
                ErrorResponseDTO.builder()
                        .message("Você não tem acesso a esse recurso")
                        .code(HttpStatus.UNAUTHORIZED.value())
                        .key(ex.getMessage())
                        .build(),

                HttpStatus.UNAUTHORIZED);
    }

    private String extractDetailMessage(String message) {
        String detail = "Erro ao processar a requisição.";
        if (message != null) {

            String[] parts = message.split("\n");
            for (String part : parts) {
                final String contains = "Detalhe:";
                if (part.contains(contains)) {

                    detail = part.substring(part.indexOf(contains) + contains.length()).trim();
                    break;
                }
            }
        }
        return detail;
    }

    @SuppressWarnings("null")
    @Override
    protected ResponseEntity<Object> createResponseEntity(Object body, HttpHeaders headers, HttpStatusCode statusCode,
            WebRequest request) {

        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .message(request.toString())
                .code(statusCode.value())
                .key(((ProblemDetail) body).getDetail())
                .build();
        return ResponseEntity.status(statusCode.value()).body(errorResponseDTO);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<ErrorResponseDTO> handleNotFoundException(NotFoundException exception) {
        logger.error(exception);
        return exception.getMessageError();
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<ErrorResponseDTO> handleUsernameNotFoundException(UsernameNotFoundException exception) {
        logger.error(exception);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ErrorResponseDTO.builder()
                        .message(exception.getCause().getMessage())
                        .code(404)
                        .key(exception.getMessage() != null ? exception.getMessage()
                                : "")
                        .build());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<ErrorResponseDTO> handleBadCredentialsException(BadCredentialsException exception) {
        logger.error(exception);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ErrorResponseDTO.builder()
                        .message(exception.getMessage() != null ? exception.getMessage() : null)
                        .code(401)
                        .build());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ErrorResponseDTO> handleBadRequestException(BadRequestException exception) {
        logger.error(exception);
        return exception.getMessageError();
    }

    private ErrorResponseDTO getErrorResponse(MethodArgumentNotValidException ex, HttpStatus status,
            List<String> errors) {
        return ErrorResponseDTO.builder()
                .message("Requisição possui campos inválidos")
                .code(status.value())
                .key(status.getReasonPhrase()).errors(errors)
                .build();

    }

    @SuppressWarnings("null")
    private List<String> getErrors(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors().stream()
                .map(error -> {
                    String messageRejected = "";
                    if (error.getRejectedValue() != null) {
                        messageRejected = error.getRejectedValue().toString();
                    }
                    return error.getDefaultMessage()
                            .concat(" - ")
                            .concat(error.getField())
                            .concat(" - ")
                            .concat(messageRejected);
                }

                ).toList();
    }
}
