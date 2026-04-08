package br.edu.ifsp.foodflow.app.domain.order.dto;

import java.util.List;
import java.util.UUID;

public record AddItemToOrderRequest(
        UUID orderId,
        UUID menuItemId,
        String observations,
        List<UUID> addOnIds,
        UUID waiterId
) {}
