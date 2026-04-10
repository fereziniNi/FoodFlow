package br.edu.ifsp.foodflow.app.infra.persistence.repository.springdata;

import br.edu.ifsp.foodflow.app.infra.persistence.entity.AddOnJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SpringDataAddOnRepository extends JpaRepository<AddOnJpaEntity, UUID> {
    List<AddOnJpaEntity> findAllById(List<UUID> ids);
}
