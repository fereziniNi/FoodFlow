package br.edu.ifsp.foodflow.app.domain.order.mapper;

import br.edu.ifsp.foodflow.app.domain.order.OrderEntity;
import br.edu.ifsp.foodflow.app.domain.order.dto.OrderDTO;
import br.edu.ifsp.foodflow.app.domain.orderItem.OrderItemEntity;
import br.edu.ifsp.foodflow.app.domain.orderItem.dto.OrderItemDTO;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderDTO toDTO(OrderEntity order) {
        List<OrderItemDTO> items = order.getOrderItems().stream()
                .map(OrderMapper::toOrderItemDTO)
                .collect(Collectors.toList());

        return new OrderDTO(
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

    private static OrderItemDTO toOrderItemDTO(OrderItemEntity item) {
        return new OrderItemDTO(
                item.getMenuItem().getName(),
                item.getMenuItem().getDescription(),
                item.getPrice()
        );
    }
}