package br.edu.ifsp.foodflow.app.domain.order.dto;

import br.edu.ifsp.foodflow.app.domain.orderItem.OrderItemStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record AdvanceOrderItemStatusDTO(
        UUID itemId,
        UUID orderId,
        OrderItemStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
}
