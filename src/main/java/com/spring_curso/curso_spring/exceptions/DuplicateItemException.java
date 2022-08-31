package com.spring_curso.curso_spring.exceptions;

public class DuplicateItemException extends RuntimeException{
    public DuplicateItemException(){
        super("The list have a repeated item.");
    }
}
