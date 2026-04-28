package br.edu.ifsp.foodflow.app.domain.orderItem;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderItemRepository {
    OrderItem save(OrderItem orderItem);
    Optional<OrderItem> findById(UUID id);
    List<OrderItem> findByStatus(OrderItemStatus status);
}