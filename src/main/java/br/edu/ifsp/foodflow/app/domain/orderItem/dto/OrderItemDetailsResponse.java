package br.edu.ifsp.foodflow.app.domain.orderItem.dto;

public record OrderItemDetailsResponse(
        String name,
        String description,
        double price
) {}