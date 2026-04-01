package br.edu.ifsp.foodflow.app.domain.addOn;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.util.UUID;

@Getter
public class AddOnEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private Double price;

    public AddOnEntity(UUID id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
