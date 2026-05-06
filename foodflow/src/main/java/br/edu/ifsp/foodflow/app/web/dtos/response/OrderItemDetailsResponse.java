package br.edu.ifsp.foodflow.app.web.dtos.response;

import br.edu.ifsp.foodflow.app.domain.orderItem.OrderItemStatus;

import java.util.UUID;

public record OrderItemDetailsResponse(
        UUID id,
        String name,
        String observations,
        double price,
        OrderItemStatus status,
        String waiterName
) {}