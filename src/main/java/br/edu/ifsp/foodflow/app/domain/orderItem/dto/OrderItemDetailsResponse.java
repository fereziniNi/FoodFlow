package br.edu.ifsp.foodflow.app.domain.orderItem.dto;

import java.util.UUID;

public record OrderItemDetailsResponse(
        UUID id,
        String description,
        double price
) {}