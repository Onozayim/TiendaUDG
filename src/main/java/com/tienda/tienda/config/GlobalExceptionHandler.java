package com.tienda.tienda.config;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.tienda.tienda.vars.JSONDataObject;
import com.tienda.tienda.vars.JSONMessageObject;
import com.tienda.tienda.vars.StringConsts;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<?> notValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> errors = new ArrayList<>();

        ex.getAllErrors().forEach(err -> errors.add(err.getDefaultMessage()));

        Map<String, List<String>> result = new HashMap<>();
        result.put("errors", errors);   

        JSONDataObject jsonObject = new JSONDataObject<Map<String, List<String>>>();

        jsonObject.setData(result);
        jsonObject.setMessage("Error a la hora de registrarse");
        jsonObject.setStatus(StringConsts.Error);

        return new ResponseEntity<>(jsonObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<?> sqlException(DataIntegrityViolationException ex, HttpServletRequest request) {
        JSONMessageObject jsonObject = new JSONMessageObject();

        jsonObject.setMessage(StringConsts.Expecion);
        jsonObject.setStatus(StringConsts.Error);

        System.out.println("EXCEPCION SQL");
        System.out.println(ex.getMessage());

        return new ResponseEntity<>(jsonObject, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> generalException(Exception ex, HttpServletRequest request) {
        JSONMessageObject jsonObject = new JSONMessageObject();

        jsonObject.setMessage(StringConsts.Expecion);
        jsonObject.setStatus(StringConsts.Error);

        System.out.println("EXCEPCION");
        System.out.println(ex.getMessage());
        System.out.println(ex.getClass());

        return new ResponseEntity<>(jsonObject, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
