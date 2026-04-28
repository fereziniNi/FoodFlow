package br.edu.ifsp.foodflow.app.domain.addOn;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class AddOn {
    private UUID id;
    private String name;
    private Double price;

    public AddOn(String name, Double price) {
        this.name = name;
        this.price = price;
    }
}
