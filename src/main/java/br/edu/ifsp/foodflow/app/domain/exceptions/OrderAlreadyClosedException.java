package br.edu.ifsp.foodflow.app.domain.exceptions;

public class OrderAlreadyClosedException extends RuntimeException{
    public OrderAlreadyClosedException(String message) {
        super(message);
    }
}
