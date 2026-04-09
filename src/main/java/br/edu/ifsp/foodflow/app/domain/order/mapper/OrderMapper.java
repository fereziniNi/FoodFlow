package br.edu.ifsp.foodflow.app.domain.order.mapper;

import br.edu.ifsp.foodflow.app.domain.order.Order;
import br.edu.ifsp.foodflow.app.domain.order.dto.OrderDetailsResponse;
import br.edu.ifsp.foodflow.app.domain.orderItem.OrderItem;
import br.edu.ifsp.foodflow.app.domain.orderItem.dto.OrderItemDetailsResponse;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderDetailsResponse toDTO(Order order) {
        List<OrderItemDetailsResponse> items = order.getOrderItems().stream()
                .map(OrderMapper::toOrderItemDTO)
                .collect(Collectors.toList());

        return new OrderDetailsResponse(
                order.getId(),
                order.getTable().getTableNumber(),
                order.getUser().getName(),
                order.getCreatedAt(),
                order.getActive(),
                order.getTotalPriceOfOrder(),
                order.getDiscountPercentage(),
                items
        );
    }

    private static OrderItemDetailsResponse toOrderItemDTO(OrderItem item) {
        return new OrderItemDetailsResponse(
                item.getMenuItem().getName(),
                item.getMenuItem().getDescription(),
                item.getPrice()
        );
    }
}