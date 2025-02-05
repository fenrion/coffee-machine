package com.aisa.test.advice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private String getTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }

//    private ErrorResponse errorResponseBuilder(Exception exception, String title, String message, HttpServletRequest request) {
//        //log.info(getTime() + "\n" + exception.getMessage());
//        //log.info(request.getMethod() + " " + request.getRequestURI());
//
//        return ErrorResponse.builder()
//                .title(title)
//                .detail(message)
//                .request(request.getMethod() + " " + request.getRequestURI())
//                .time(getTime())
//                .build();
//    }

    @Value
    @Builder
    public static class ErrorResponse {
        String title;
        String detail;
        String request;
        String time;
    }
}
