package com.aisa.test.advice;

import com.aisa.test.domain.exception.NotEnoughFundsException;
import com.aisa.test.domain.exception.NotEnoughIngredientsException;
import com.aisa.test.domain.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.aisa.test.constants.ConfigurationConstants.*;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException exception, HttpServletRequest request) {
        return new ErrorResponse(DATA_NOT_FOUND, exception.getMessage(), request.getMethod() + " " + request.getRequestURI(), getTime());
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationExceptionBadRequest(BindException exception, HttpServletRequest request) {
        return new ErrorResponse(INPUT_DATA_ERROR, exception.getMessage(), request.getMethod() + " " + request.getRequestURI(), getTime());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleFeignExceptionBadRequest(ConstraintViolationException exception, HttpServletRequest request) {
        return new ErrorResponse(INPUT_DATA_ERROR, exception.getMessage(), request.getMethod() + " " + request.getRequestURI(), getTime());
    }

    @ExceptionHandler(NotEnoughFundsException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleNotEnoughFundsExceptions(NotEnoughFundsException exception, HttpServletRequest request) {
        return new ErrorResponse(NOT_ENOUGH_FUNDS, exception.getMessage(), request.getMethod() + " " + request.getRequestURI(), getTime());
    }

    @ExceptionHandler(NotEnoughIngredientsException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleNotEnoughIngredientsException(NotEnoughIngredientsException exception, HttpServletRequest request) {
        return new ErrorResponse(NOT_ENOUGH_INGREDIENTS, exception.getMessage(), request.getMethod() + " " + request.getRequestURI(), getTime());
    }

        public record ErrorResponse(String title, String detail, String request, String time) {
    }
    private String getTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }
}
