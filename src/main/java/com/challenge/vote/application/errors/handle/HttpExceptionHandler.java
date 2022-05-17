package com.challenge.vote.application.errors.handle;

import com.challenge.vote.application.errors.badrequest.BadRequestException;
import com.challenge.vote.application.errors.notfound.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@Slf4j
public class HttpExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<Object> handleNotFound(RuntimeException ex, WebRequest webRequest) {
        final var path = ((ServletWebRequest) webRequest).getRequest().getRequestURI();
        final var body = HttpExceptionHandlerOutput.builder()
                .code(NOT_FOUND.value())
                .message(ex.getMessage())
                .path(path)
                .build();
        log.error(ex.getMessage(), ex);
        return this.handleExceptionInternal(
                ex,
                body,
                new HttpHeaders(),
                NOT_FOUND,
                webRequest
        );
    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<Object> handleBadRequest(RuntimeException ex, WebRequest webRequest) {
        final var path = ((ServletWebRequest) webRequest).getRequest().getRequestURI();
        final var body = HttpExceptionHandlerOutput.builder()
                .code(BAD_REQUEST.value())
                .message(ex.getMessage())
                .path(path)
                .build();
        log.error(ex.getMessage(), ex);
        return this.handleExceptionInternal(
                ex,
                body,
                new HttpHeaders(),
                BAD_REQUEST,
                webRequest
        );
    }

    @ExceptionHandler({RuntimeException.class, Exception.class})
    protected ResponseEntity<Object> handleServerError(RuntimeException ex, WebRequest webRequest) {
        final var path = ((ServletWebRequest) webRequest).getRequest().getRequestURI();
        final var body = HttpExceptionHandlerOutput.builder()
                .code(INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .path(path)
                .build();
        log.error(ex.getMessage(), ex);
        return this.handleExceptionInternal(
                ex,
                body,
                new HttpHeaders(),
                INTERNAL_SERVER_ERROR,
                webRequest
        );
    }
}
