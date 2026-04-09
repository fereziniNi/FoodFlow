package br.edu.ifsp.foodflow.app.domain.menuItem;

import lombok.Getter;

import java.util.UUID;

@Getter
public class MenuItem {
    private UUID id;
    private String name;
    private String description;
    private Double price;
    private Integer availableQuantity;

    public MenuItem(UUID id, String name, String description, Double price, Integer availableQuantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.availableQuantity = availableQuantity;
    }
}
