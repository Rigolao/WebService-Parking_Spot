package com.spring_curso.curso_spring.exceptions;

public class EmptyListException extends RuntimeException{
    public EmptyListException(){
        super("Impossible to save a empty list.");
    }
}
