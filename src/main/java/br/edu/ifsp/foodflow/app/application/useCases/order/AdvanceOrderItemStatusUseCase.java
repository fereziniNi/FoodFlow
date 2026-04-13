package br.edu.ifsp.foodflow.app.application.useCases.order;

import br.edu.ifsp.foodflow.app.domain.exceptions.OrderItemNotFoundException;
import br.edu.ifsp.foodflow.app.domain.exceptions.OrderNotFoundException;
import br.edu.ifsp.foodflow.app.domain.order.Order;
import br.edu.ifsp.foodflow.app.domain.order.OrderRepository;
import br.edu.ifsp.foodflow.app.domain.order.dto.AdvanceOrderItemStatusDTO;
import br.edu.ifsp.foodflow.app.domain.orderItem.OrderItem;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
public class AdvanceOrderItemStatusUseCase {
    private  final OrderRepository orderRepository;

    public AdvanceOrderItemStatusUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public AdvanceOrderItemStatusDTO advanceStatus(UUID orderId, UUID itemId){
        Objects.requireNonNull(orderId,"O ID do pedido não pode ser nulo");
        Objects.requireNonNull(itemId,"O ID do item não pode ser nulo");

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Pedido não encontrada para o ID: " + orderId));
        OrderItem orderItem = order.getOrderItems()
                .stream()
                .filter(item-> item.getId().equals(itemId))
                .findFirst().orElseThrow(()-> new OrderItemNotFoundException("Item não encontrado na comanda: "+ itemId)
                );

        orderItem.upgradeProgress();
        orderRepository.save(order);
        return new AdvanceOrderItemStatusDTO(
                orderItem.getId(),
                orderId,
                orderItem.getStatus(),
                orderItem.getCreateAt(),
                orderItem.getUpdateAt()
        );
    }
}
