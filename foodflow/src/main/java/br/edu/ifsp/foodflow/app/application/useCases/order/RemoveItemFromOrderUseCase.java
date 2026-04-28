package br.edu.ifsp.foodflow.app.application.useCases.order;

import br.edu.ifsp.foodflow.app.domain.order.Order;
import br.edu.ifsp.foodflow.app.domain.order.OrderRepository;
import br.edu.ifsp.foodflow.app.domain.order.dto.OrderResultDTO;
import br.edu.ifsp.foodflow.app.domain.order.dto.RemoveItemFromOrderDTO;
import br.edu.ifsp.foodflow.app.domain.orderItem.OrderItem;
import br.edu.ifsp.foodflow.app.domain.orderItem.OrderItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

@Service
public class RemoveItemFromOrderUseCase {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public RemoveItemFromOrderUseCase(OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional
    public OrderResultDTO execute(RemoveItemFromOrderDTO request){
        UUID orderId = request.orderId();
        Objects.requireNonNull(orderId, "O ID da comanda é obrigatório e deve ser válido.");

        UUID orderItemId = request.orderItemId();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("Pedido não encontrado para o ID: " + orderId));
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new NoSuchElementException("Item solicitado não encontrado para o ID: " + orderItemId));

        order.removeOrderItem(orderItem);
        orderRepository.save(order);

        return new OrderResultDTO(order.getId(), order.getTable().getTableNumber(), order.getCreatedAt(), order.getActive(), order.getTotalPriceOfOrder());
    }
}
