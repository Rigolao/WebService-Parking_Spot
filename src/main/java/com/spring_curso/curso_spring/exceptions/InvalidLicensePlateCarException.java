package com.spring_curso.curso_spring.exceptions;

public class InvalidLicensePlateCarException extends RuntimeException {
    public InvalidLicensePlateCarException(){
        super("Conflict: Lincense Plate Car is alredy in use!");
    }
}
