package br.edu.ifsp.foodflow.app.domain.order.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderResponse(
        UUID orderId,
        Integer tableNumber,
        LocalDateTime createdAt,
        Boolean active,
        Double total
) {
}
