package br.edu.ifsp.foodflow.app.domain.order.dto;

import br.edu.ifsp.foodflow.app.domain.orderItem.dto.OrderItemDetailsResponse;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDetailsResponse(
        Integer orderId,
        int tableNumber,
        String userName,
        LocalDateTime createdAt,
        boolean active,
        double total,
        double discount,
        List<OrderItemDetailsResponse> items
) {}