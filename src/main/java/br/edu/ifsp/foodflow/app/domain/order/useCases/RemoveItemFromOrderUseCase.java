package br.edu.ifsp.foodflow.app.domain.order.useCases;

import br.edu.ifsp.foodflow.app.domain.addOn.AddOnRepository;
import br.edu.ifsp.foodflow.app.domain.menuItem.MenuItemRepository;
import br.edu.ifsp.foodflow.app.domain.order.OrderEntity;
import br.edu.ifsp.foodflow.app.domain.order.OrderRepository;
import br.edu.ifsp.foodflow.app.domain.order.dto.OrderResponse;
import br.edu.ifsp.foodflow.app.domain.order.dto.RemoveItemFromOrderRequest;
import br.edu.ifsp.foodflow.app.domain.orderItem.OrderItemEntity;
import br.edu.ifsp.foodflow.app.domain.orderItem.OrderItemRepository;
import br.edu.ifsp.foodflow.app.domain.user.UserRepository;

import java.util.NoSuchElementException;
import java.util.UUID;

public class RemoveItemFromOrderUseCase {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public RemoveItemFromOrderUseCase(OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public OrderResponse execute(RemoveItemFromOrderRequest request){
        UUID orderId = request.orderId();
        UUID orderItemId = request.orderItemId();

        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("Pedido não encontrado para o ID: " + orderId));
        OrderItemEntity orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new NoSuchElementException("Item solicitado não encontrado para o ID: " + orderItemId));

        order.removeOrderItem(orderItem);
        orderRepository.save(order);

        return new OrderResponse(order.getId(), order.getTable().getTableNumber(), order.getCreatedAt(), order.getActive(), order.getTotalPriceOfOrder());
    }
}
