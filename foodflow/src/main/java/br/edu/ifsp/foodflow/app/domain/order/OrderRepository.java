package br.edu.ifsp.foodflow.app.domain.order;

import br.edu.ifsp.foodflow.app.domain.table.Table;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    Optional<Order> findActiveOrderByTable(Table table);
    Order save(Order order);
    Optional<Order> findById(UUID id);
}