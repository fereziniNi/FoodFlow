package br.edu.ifsp.foodflow.app.application.useCases.menuItem;

import br.edu.ifsp.foodflow.app.domain.menuItem.MenuItem;
import br.edu.ifsp.foodflow.app.domain.menuItem.MenuItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListMenuItemsUseCase {
    private final MenuItemRepository menuItemRepository;

    public ListMenuItemsUseCase(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public List<MenuItem> execute() {
        return menuItemRepository.findAll();
    }
}
