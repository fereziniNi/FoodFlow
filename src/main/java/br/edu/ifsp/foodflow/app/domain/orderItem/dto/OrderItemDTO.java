package br.edu.ifsp.foodflow.app.domain.orderItem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderItemDTO {
    private String name;
    private String description;
    private double price;
}