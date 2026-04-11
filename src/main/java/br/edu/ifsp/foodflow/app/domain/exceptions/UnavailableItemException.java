package br.edu.ifsp.foodflow.app.domain.exceptions;

public class UnavailableItemException extends RuntimeException{
    public UnavailableItemException(String message) {
        super(message);
    }
}
