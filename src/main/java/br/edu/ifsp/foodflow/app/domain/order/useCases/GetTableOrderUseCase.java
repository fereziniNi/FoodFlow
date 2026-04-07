package br.edu.ifsp.foodflow.app.domain.order.useCases;

import br.edu.ifsp.foodflow.app.domain.order.OrderEntity;
import br.edu.ifsp.foodflow.app.domain.order.OrderRepository;

import java.util.UUID;

public class GetTableOrderUseCase {
    private final OrderRepository orderRepository;

    public GetTableOrderUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    public OrderEntity getOrderById(UUID orderId){
        if(orderId == null) throw new IllegalArgumentException("O ID da mesa é obrigatório.");

        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));
    }
}
