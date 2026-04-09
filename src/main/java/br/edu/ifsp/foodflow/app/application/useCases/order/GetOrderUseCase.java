package br.edu.ifsp.foodflow.app.application.useCases.order;

import br.edu.ifsp.foodflow.app.domain.order.Order;
import br.edu.ifsp.foodflow.app.domain.order.OrderRepository;
import br.edu.ifsp.foodflow.app.domain.order.dto.OrderDetailsResponse;
import br.edu.ifsp.foodflow.app.domain.order.mapper.OrderMapper;

import java.util.UUID;

public class GetOrderUseCase {
    private final OrderRepository orderRepository;

    public GetOrderUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    public OrderDetailsResponse getOrderById(UUID orderId) {
        if(orderId == null)
            throw new IllegalArgumentException("O Id do pedido é obrigatório.");

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado."));

        return OrderMapper.toDTO(order);
    }
}
