package br.edu.ifsp.foodflow.app.domain.menuItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MenuItemRepository {
    MenuItem save(MenuItem menuItem);
    Optional<MenuItem> findById(UUID id);
    List<MenuItem> findAll();
    void deleteById(UUID id);
}