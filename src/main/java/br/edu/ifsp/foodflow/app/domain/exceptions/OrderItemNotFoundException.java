package br.edu.ifsp.foodflow.app.domain.exceptions;

public class OrderItemNotFoundException extends RuntimeException  {
    public OrderItemNotFoundException(String message) {
        super(message);
    }
}
