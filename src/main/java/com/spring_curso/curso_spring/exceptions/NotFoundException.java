package com.spring_curso.curso_spring.exceptions;

public class NotFoundException extends RuntimeException{
    public NotFoundException(){
        super("Parking Spot not found.");
    }
}
