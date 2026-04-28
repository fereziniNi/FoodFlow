package br.edu.ifsp.foodflow.app.infra.persistence.repository.springdata;

import br.edu.ifsp.foodflow.app.domain.orderItem.OrderItemStatus;
import br.edu.ifsp.foodflow.app.infra.persistence.entity.OrderItemJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SpringDataOrderItemRepository extends JpaRepository<OrderItemJpaEntity, UUID> {
    List<OrderItemJpaEntity> findByStatus(OrderItemStatus status);
}
