package br.edu.ifsp.foodflow.app.domain.orderItem;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, UUID> {
}
