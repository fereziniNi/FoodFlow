package br.edu.ifsp.foodflow.app.infra.persistence.repository.springdata;

import br.edu.ifsp.foodflow.app.infra.persistence.entity.OrderItemJpaEntity;
import br.edu.ifsp.foodflow.app.infra.persistence.entity.OrderJpaEntity;
import br.edu.ifsp.foodflow.app.infra.persistence.entity.TableJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataOrderRepository extends JpaRepository<OrderJpaEntity, UUID> {
    Optional<OrderJpaEntity> findByTableAndActiveTrue(TableJpaEntity table);
}