package br.edu.ifsp.foodflow.app.infra.exceptions;

public class UnavailableItemException extends RuntimeException{
    public UnavailableItemException(String message) {
        super(message);
    }
}
