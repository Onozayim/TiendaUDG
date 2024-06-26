package com.tienda.tienda.config;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.security.auth.login.AccountLockedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tienda.tienda.responses.JsonResponses;
import com.tienda.tienda.vars.StringConsts;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    JsonResponses jsonResponses;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<?> notValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> errors = new ArrayList<>();

        ex.getAllErrors().forEach(err -> errors.add(err.getDefaultMessage()));

        Map<String, List<String>> result = new HashMap<>();
        result.put("errors", errors);   

        return jsonResponses.ReturnErrorData(result, "Argumentos incorrectos");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<?> sqlException(DataIntegrityViolationException ex, HttpServletRequest request) {
        System.out.println("EXCEPCION SQL");
        System.out.println(ex.getMessage());

        return this.jsonResponses.ReturnErrorMessage(
            StringConsts.Expecion, 
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(ExpiredJwtException.class)
    protected ResponseEntity<?> jwtExpired(ExpiredJwtException ex, HttpServletRequest request) {
        return this.jsonResponses.ReturnErrorMessage(
            StringConsts.JwtExpired, 
            HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<?> badCredentials(BadCredentialsException ex, HttpServletRequest request) {
        return this.jsonResponses.ReturnErrorMessage(
            "Credenciales incorrectas",
            HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(AccountLockedException.class)
    protected ResponseEntity<?> accoutLocked(AccountLockedException ex, HttpServletRequest request) {
        return this.jsonResponses.ReturnErrorMessage(
            "Cuenta blockeada",
            HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(SignatureException.class)
    protected ResponseEntity<?> invalidJWT(SignatureException ex, HttpServletRequest request) {
        return this.jsonResponses.ReturnErrorMessage(
            "Token invalido",
            HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(MalformedJwtException.class) 
    protected ResponseEntity<?> malformedJwtException(MalformedJwtException ex, HttpServletRequest request) {
        return this.jsonResponses.ReturnErrorMessage(
            StringConsts.JwtNotValid,
            HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    protected ResponseEntity<?> authorizationDenied(AuthorizationDeniedException ex, HttpServletRequest request) {
        return this.jsonResponses.ReturnErrorMessage(
            "Permiso no valido",
            HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<?> noSuchElement(NoSuchElementException ex, HttpServletRequest request) {
        return this.jsonResponses.ReturnErrorMessage(
            ex.getMessage(),
            HttpStatus.NOT_FOUND
        );
    }
    
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> generalException(Exception ex, HttpServletRequest request) {
        System.out.println("EXCEPCION");
        System.out.println(ex.getMessage());
        System.out.println(ex.getClass());
        // ex.printStackTrace();

        return this.jsonResponses.ReturnErrorMessage(
            StringConsts.Expecion, 
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
