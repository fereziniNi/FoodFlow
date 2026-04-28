package br.edu.ifsp.foodflow.app.web.dtos.response;

import br.edu.ifsp.foodflow.app.domain.orderItem.OrderItemStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record AdvanceOrderItemStatusResponse(
        UUID itemId,
        UUID orderId,
        OrderItemStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
}
