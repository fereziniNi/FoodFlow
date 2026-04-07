package br.edu.ifsp.foodflow.app.domain.order.useCases;

import br.edu.ifsp.foodflow.app.domain.order.OrderEntity;
import br.edu.ifsp.foodflow.app.domain.order.OrderRepository;
import br.edu.ifsp.foodflow.app.domain.order.dto.OrderDTO;
import br.edu.ifsp.foodflow.app.domain.order.mapper.OrderMapper;

import java.util.UUID;

public class GetTableOrderUseCase {
    private final OrderRepository orderRepository;

    public GetTableOrderUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    public OrderDTO getOrderById(UUID orderId) {
        if(orderId == null)
            throw new IllegalArgumentException("O Id do pedido é obrigatório.");

        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado."));

        return OrderMapper.toDTO(order);
    }
}
