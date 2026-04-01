package br.edu.ifsp.foodflow.app.domain.menuItem;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MenuItemRepository extends JpaRepository<MenuItemEntity, UUID> {
}
