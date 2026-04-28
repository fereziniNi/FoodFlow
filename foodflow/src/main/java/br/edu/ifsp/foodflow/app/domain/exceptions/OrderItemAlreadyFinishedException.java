package br.edu.ifsp.foodflow.app.domain.exceptions;

public class OrderItemAlreadyFinishedException extends RuntimeException {
    public OrderItemAlreadyFinishedException(String message) {
        super(message);
    }
}
