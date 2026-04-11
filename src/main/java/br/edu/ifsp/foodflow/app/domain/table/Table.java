package br.edu.ifsp.foodflow.app.domain.table;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Table {
    private Integer tableNumber;
    private TableStatus status;

    public Table(Integer number) {
        this.tableNumber = number;
        this.status = TableStatus.AVAILABLE;
    }

    public void markAsAvailable() {
        this.status = TableStatus.AVAILABLE;
    }



}
