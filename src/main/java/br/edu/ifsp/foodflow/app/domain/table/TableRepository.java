package br.edu.ifsp.foodflow.app.domain.table;

import java.util.List;
import java.util.Optional;

public interface TableRepository {
    Table save(Table table);
    Optional<Table> findByTableNumber(Integer tableNumber);
    List<Table> findAll();
}