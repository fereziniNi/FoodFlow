package br.edu.ifsp.foodflow.app.domain.order.dto;

import java.util.UUID;

public record RemoveItemFromOrderRequest(
        UUID orderId,
        UUID orderItemId
) {
}
