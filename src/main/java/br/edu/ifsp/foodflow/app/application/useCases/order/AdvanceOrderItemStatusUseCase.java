package br.edu.ifsp.foodflow.app.application.useCases.order;

import br.edu.ifsp.foodflow.app.domain.order.Order;
import br.edu.ifsp.foodflow.app.domain.order.OrderRepository;
import br.edu.ifsp.foodflow.app.infra.exceptions.OrderNotFoundException;

import java.util.Objects;
import java.util.UUID;

public class AdvanceOrderItemStatusUseCase {
    private  final OrderRepository orderRepository;

    public AdvanceOrderItemStatusUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void advanceStatus(UUID orderId, UUID itemId){
        Objects.requireNonNull(orderId,"O ID do pedido não pode ser nulo");
        Objects.requireNonNull(itemId,"O ID do item não pode ser nulo");

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Pedido não encontrada para o ID: " + orderId));
    }
}
