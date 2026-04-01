package br.edu.ifsp.foodflow.app.domain.orderItem;

public enum OrderItemStatusENUM {
    PENDING("pending"),
    PREPARATION("preparation"),
    FINISHED("finished");

    private final String status;
    OrderItemStatusENUM(String status){
        this.status = status;
    }
}
