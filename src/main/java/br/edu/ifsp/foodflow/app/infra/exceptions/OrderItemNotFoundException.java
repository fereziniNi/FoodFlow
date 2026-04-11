package br.edu.ifsp.foodflow.app.infra.exceptions;

public class OrderItemNotFoundException extends RuntimeException{
    public OrderItemNotFoundException(String message) {
        super(message);
    }
}
