package br.edu.ifsp.foodflow.app.domain.table;

public enum TableStatus {
    AVAILABLE("available"),
    OCCUPIED("occupied"),
    CLOSED("closed"),
    RESERVED("reserved");

    private final String status;
    TableStatus(String status){
        this.status = status;
    }
}
