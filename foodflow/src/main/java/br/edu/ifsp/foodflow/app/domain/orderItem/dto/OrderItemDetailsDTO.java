package br.edu.ifsp.foodflow.app.domain.orderItem.dto;

import br.edu.ifsp.foodflow.app.domain.orderItem.OrderItemStatus;

import java.util.UUID;

public record OrderItemDetailsDTO(
        UUID id,
        String name,
        String observations,
        double price,
        OrderItemStatus status,
        String waiterName
) {}