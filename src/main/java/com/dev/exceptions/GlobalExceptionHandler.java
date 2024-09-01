package com.dev.exceptions;

import com.dev.services.exceptions.EmailJaExistenteException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> tratarErrosDeValidacaoDeCampos(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, Object> corpo = new HashMap<>();
        corpo.put("timestamp", LocalDateTime.now());
        corpo.put("status", HttpStatus.BAD_REQUEST.value());
        corpo.put("error", "Bad Request");

        Map<String, String> erros = new HashMap<>();
        for (FieldError erroCampo : ex.getBindingResult().getFieldErrors()) {
            erros.put(erroCampo.getField(), erroCampo.getDefaultMessage());
        }
        corpo.put("message", "Erro de validação");
        corpo.put("errors", erros);
        corpo.put("path", request.getDescription(false).substring(4));

        return new ResponseEntity<>(corpo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailJaExistenteException.class)
    public ResponseEntity<Object> tratarExcecaoEmailJaExiste(EmailJaExistenteException ex, WebRequest request) {
        Map<String, Object> corpo = new HashMap<>();
        corpo.put("timestamp", LocalDateTime.now());
        corpo.put("status", HttpStatus.BAD_REQUEST.value());
        corpo.put("error", "Bad Request");
        corpo.put("message", ex.getMessage());
        corpo.put("path", request.getDescription(false).substring(4));

        return new ResponseEntity<>(corpo, HttpStatus.BAD_REQUEST);
    }
}