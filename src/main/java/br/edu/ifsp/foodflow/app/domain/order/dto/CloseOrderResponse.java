package br.edu.ifsp.foodflow.app.domain.order.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record CloseOrderResponse(
        UUID orderId,
        Integer tableNumber,
        LocalDateTime createdAt,
        Double totalWithoutDiscount,
        Double discountPercentage,
        Double totalWithDiscount,
        Double totalPerPerson


) {}
