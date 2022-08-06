package com.spring_curso.curso_spring.exceptions;

public class InvalidApartmentAndBlockException extends RuntimeException{
    public InvalidApartmentAndBlockException(){
        super("Conflict: Parking Spot alredy registered for this apartment/block!");
    }
}
