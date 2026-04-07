package br.edu.ifsp.foodflow.app.infra.exceptions;

public class OrderAlreadyClosedException extends RuntimeException{
    public OrderAlreadyClosedException(String message) {
        super(message);
    }
}
