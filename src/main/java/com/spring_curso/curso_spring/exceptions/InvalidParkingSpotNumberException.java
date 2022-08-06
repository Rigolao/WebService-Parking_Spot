package com.spring_curso.curso_spring.exceptions;

public class InvalidParkingSpotNumberException extends RuntimeException{
    public InvalidParkingSpotNumberException(){
        super("Conflict: Parking Spot Number is alredy in use!");
    }
}
