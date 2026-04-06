package br.edu.ifsp.foodflow.app.domain.orderItem;

public enum OrderItemStatus {
    PENDING("pending"),
    PREPARATION("preparation"),
    FINISHED("finished");

    private final String status;
    OrderItemStatus(String status){
        this.status = status;
    }
}
