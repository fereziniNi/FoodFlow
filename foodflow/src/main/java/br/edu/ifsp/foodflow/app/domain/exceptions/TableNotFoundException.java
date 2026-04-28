package br.edu.ifsp.foodflow.app.infra.exceptions;

public class TableNotFoundException extends RuntimeException {
    public TableNotFoundException(String message) {
        super(message);
    }
}
