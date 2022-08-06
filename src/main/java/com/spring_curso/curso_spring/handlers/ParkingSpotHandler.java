package com.spring_curso.curso_spring.handlers;

import com.spring_curso.curso_spring.controllers.ParkingSpotController;
import com.spring_curso.curso_spring.exceptions.InvalidApartmentAndBlockException;
import com.spring_curso.curso_spring.exceptions.InvalidLicensePlateCarException;
import com.spring_curso.curso_spring.exceptions.InvalidParkingSpotNumberException;
import com.spring_curso.curso_spring.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;

@ControllerAdvice(basePackageClasses = ParkingSpotController.class)
public class ParkingSpotHandler extends ResponseEntityExceptionHandler {

    private LinkedHashMap<Object, Object> _fillErrorBodyMessage(String message) {
        var body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", message);
        return body;
    }

    @ExceptionHandler(InvalidLicensePlateCarException.class)
    public ResponseEntity<Object> handleInvalidLicensePlateCarException(InvalidLicensePlateCarException ex){
        var body = _fillErrorBodyMessage(ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidParkingSpotNumberException.class)
    public ResponseEntity<Object> handleInvalidParkingSpotNumberException(InvalidParkingSpotNumberException ex){
        var body = _fillErrorBodyMessage(ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidApartmentAndBlockException.class)
    public ResponseEntity<Object> handleInvalidApartmentAndBlockException(InvalidApartmentAndBlockException ex){
        var body = _fillErrorBodyMessage(ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex){
        var body = _fillErrorBodyMessage(ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericError(Exception ex){
        var body = _fillErrorBodyMessage(ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
