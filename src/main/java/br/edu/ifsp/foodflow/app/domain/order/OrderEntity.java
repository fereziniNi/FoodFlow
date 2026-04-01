package br.edu.ifsp.foodflow.app.domain.order;

import br.edu.ifsp.foodflow.app.domain.orderItem.OrderItemEntity;
import br.edu.ifsp.foodflow.app.domain.table.TableEntity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private TableEntity table;
    private List<OrderItemEntity> tableOrders;
    private LocalDateTime createdAt;
    private Boolean active;

    public OrderEntity(UUID id, TableEntity table, List<OrderItemEntity> tableOrders, LocalDateTime createdAt, Boolean active) {
        this.id = id;
        this.table = table;
        this.tableOrders = tableOrders;
        this.createdAt = createdAt;
        this.active = active;
    }
}
