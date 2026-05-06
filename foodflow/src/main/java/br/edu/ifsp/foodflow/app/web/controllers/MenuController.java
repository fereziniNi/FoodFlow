package br.edu.ifsp.foodflow.app.web.controllers;

import br.edu.ifsp.foodflow.app.application.useCases.addOn.ListAddOnsUseCase;
import br.edu.ifsp.foodflow.app.application.useCases.menuItem.ListMenuItemsUseCase;
import br.edu.ifsp.foodflow.app.domain.addOn.AddOn;
import br.edu.ifsp.foodflow.app.domain.menuItem.MenuItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {
    private final ListMenuItemsUseCase listMenuItemsUseCase;
    private final ListAddOnsUseCase listAddOnsUseCase;

    public MenuController(ListMenuItemsUseCase listMenuItemsUseCase, ListAddOnsUseCase listAddOnsUseCase) {
        this.listMenuItemsUseCase = listMenuItemsUseCase;
        this.listAddOnsUseCase = listAddOnsUseCase;
    }

    @GetMapping("/items")
    public ResponseEntity<List<MenuItem>> listItems() {
        return ResponseEntity.ok(listMenuItemsUseCase.execute());
    }

    @GetMapping("/addons")
    public ResponseEntity<List<AddOn>> listAddOns() {
        return ResponseEntity.ok(listAddOnsUseCase.execute());
    }
}
