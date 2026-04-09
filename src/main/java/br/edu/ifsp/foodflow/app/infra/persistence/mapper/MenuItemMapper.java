package br.edu.ifsp.foodflow.app.infra.persistence.mapper;

import br.edu.ifsp.foodflow.app.domain.menuItem.MenuItem;
import br.edu.ifsp.foodflow.app.infra.persistence.entity.MenuItemJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class MenuItemMapper {
    public MenuItemJpaEntity toJpaEntity(MenuItem menuItem){
        return new MenuItemJpaEntity(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getPrice(),
                menuItem.getAvailableQuantity()
        );
    }

    public MenuItem toDomain(MenuItemJpaEntity menuItemJpaEntity){
        return new MenuItem(
                menuItemJpaEntity.getId(),
                menuItemJpaEntity.getName(),
                menuItemJpaEntity.getDescription(),
                menuItemJpaEntity.getPrice(),
                menuItemJpaEntity.getAvailableQuantity()
        );
    }
}
