package br.edu.ifsp.foodflow.app.domain.order;

import br.edu.ifsp.foodflow.app.domain.orderItem.OrderItemEntity;
import br.edu.ifsp.foodflow.app.domain.table.TableEntity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private TableEntity table;
    private List<OrderItemEntity> orderItems;
    private LocalDateTime createdAt;
    private Boolean active;

    public OrderEntity(TableEntity table) {
        if (table == null) {
            throw new IllegalArgumentException("Um pedido não pode ser aberto sem uma mesa.");
        }
        this.table = table;
        this.orderItems = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
        this.active = true;
    }

    public void addOrderItem(OrderItemEntity item) {
        this.orderItems.add(item);
    }
}
