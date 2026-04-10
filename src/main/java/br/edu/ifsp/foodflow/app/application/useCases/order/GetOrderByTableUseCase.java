package br.edu.ifsp.foodflow.app.application.useCases.order;

import br.edu.ifsp.foodflow.app.domain.order.Order;
import br.edu.ifsp.foodflow.app.domain.order.OrderRepository;
import br.edu.ifsp.foodflow.app.domain.order.dto.OrderDetailsResponse;
import br.edu.ifsp.foodflow.app.domain.orderItem.dto.OrderItemDetailsResponse;
import br.edu.ifsp.foodflow.app.infra.exceptions.OrderNotFoundException;

import java.util.Objects;
import java.util.UUID;

public class GetOrderByTableUseCase {
    private final OrderRepository orderRepository;

    public GetOrderByTableUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderDetailsResponse getOrderByTable(Integer tableId) {
        Objects.requireNonNull(tableId, "O Id da mesa é obrigatório.");

        Order order = orderRepository.findById(UUID.randomUUID())
                .orElseThrow(() -> new OrderNotFoundException("Pedido não encontrado."));

        return new OrderDetailsResponse(
                tableId,
                order.getTable().getTableNumber(),
                order.getUser().getUsername(),
                order.getCreatedAt(),
                order.getActive(),
                order.getTotalPriceOfOrder(),
                order.getDiscountPercentage(),
                order.getOrderItems().stream().map(
                        item->new OrderItemDetailsResponse(
                                item.getId(),
                                item.getObservations(),
                                item.getPrice()
                )).toList()
        );
    }
}
