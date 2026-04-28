package br.edu.ifsp.foodflow.app.domain.order.dto;

import java.util.UUID;

public record RemoveItemFromOrderDTO(
        UUID orderId,
        UUID orderItemId
) {
}
