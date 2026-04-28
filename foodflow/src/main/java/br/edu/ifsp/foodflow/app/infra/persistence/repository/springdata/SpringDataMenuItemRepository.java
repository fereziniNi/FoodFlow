package br.edu.ifsp.foodflow.app.infra.persistence.repository.springdata;

import br.edu.ifsp.foodflow.app.infra.persistence.entity.MenuItemJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataMenuItemRepository extends JpaRepository<MenuItemJpaEntity, UUID> {
}
