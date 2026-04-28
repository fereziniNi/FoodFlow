package br.edu.ifsp.foodflow.app.web.dtos.response;

import br.edu.ifsp.foodflow.app.domain.orderItem.OrderItemStatus;

import java.util.UUID;

public record OrderItemDetailsResponse(
        UUID id,
        String description,
        double price,
        OrderItemStatus status
) {}