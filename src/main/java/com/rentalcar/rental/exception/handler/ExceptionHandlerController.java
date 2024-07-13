package com.rentalcar.rental.exception.handler;


import com.rentalcar.rental.dto.ErrorDto;
import com.rentalcar.rental.exception.NotFoundException;
import com.rentalcar.rental.exception.ValidationException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleServiceException(final NotFoundException e,
                                                           final HttpServletResponse response) {
        log.error(e.getMessage(), e);
        return getResponseEntity(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleServiceException(final ValidationException e,
                                                           final HttpServletResponse response) {
        log.error(e.getMessage(), e);
        return getResponseEntity(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleServiceException(final Exception e,
                                                           final HttpServletResponse response) {
        log.error(e.getMessage(), e);
        return getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errorMessage = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorString = error.getDefaultMessage();
            errorMessage.append(fieldName).append(": ").append(errorString).append(". \n");
        });
        return getResponseEntity(HttpStatus.BAD_REQUEST, errorMessage.toString());
    }

    private ResponseEntity<ErrorDto> getResponseEntity(HttpStatus httpStatus, String message) {
        return ResponseEntity
                .status(httpStatus)
                .body(ErrorDto.builder()
                        .errorMessage(message)
                        .build());
    }

}
