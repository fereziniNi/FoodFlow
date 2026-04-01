package br.edu.ifsp.foodflow.app.domain.table;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
public class TableEntity {
    @Id
    private Integer tableNumber;
    @Enumerated(EnumType.STRING)
    private TableStatusENUM status;

    public TableEntity(Integer number) {
        this.tableNumber = number;
        this.status = TableStatusENUM.AVAILABLE;
    }
}
