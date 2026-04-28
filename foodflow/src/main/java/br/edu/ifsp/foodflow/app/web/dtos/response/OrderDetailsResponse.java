package br.edu.ifsp.foodflow.app.web.dtos.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderDetailsResponse(
        UUID orderId,
        int tableNumber,
        String userName,
        LocalDateTime createdAt,
        boolean active,
        double total,
        double discount,
        List<OrderItemDetailsResponse> items
) {}