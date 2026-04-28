package br.edu.ifsp.foodflow.app.domain.order.dto;

import br.edu.ifsp.foodflow.app.domain.orderItem.dto.OrderItemDetailsDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderDetailsDTO(
        UUID orderId,
        int tableNumber,
        String userName,
        LocalDateTime createdAt,
        boolean active,
        double total,
        double discount,
        List<OrderItemDetailsDTO> items
) {}