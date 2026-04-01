package br.edu.ifsp.foodflow.app.domain.table;

public enum TableStatusENUM {
    AVAILABLE("available"),
    OCCUPIED("occupied"),
    CLOSED("closed"),
    RESERVED("reserved");

    private final String status;
    TableStatusENUM(String status){
        this.status = status;
    }
}
