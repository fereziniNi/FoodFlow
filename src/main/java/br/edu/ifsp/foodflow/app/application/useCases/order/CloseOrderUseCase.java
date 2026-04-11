package br.edu.ifsp.foodflow.app.application.useCases.order;

import br.edu.ifsp.foodflow.app.domain.order.Order;
import br.edu.ifsp.foodflow.app.domain.order.OrderRepository;
import br.edu.ifsp.foodflow.app.domain.order.dto.CloseOrderResponse;
import br.edu.ifsp.foodflow.app.infra.exceptions.OrderAlreadyClosedException;
import br.edu.ifsp.foodflow.app.infra.exceptions.OrderNotFoundException;

import java.util.Objects;
import java.util.UUID;

public class CloseOrderUseCase {

    private final OrderRepository orderRepository;

    public CloseOrderUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public CloseOrderResponse closeOrder(UUID orderId, int numberOfPeople ){
        Objects.requireNonNull(orderId,"O ID do pedido não pode ser nulo");

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Pedido não encontrado para o ID:" + orderId));
        if(!order.getActive()){
            throw new OrderAlreadyClosedException("Pedido já finalizado para o ID:"+ orderId);
        }
        if(order.getOrderItems().isEmpty()){
            throw new IllegalStateException("Pedido sem itens não pode ser finalizado para o ID:"+ orderId);
        }

        if(numberOfPeople < 1) {
            throw new IllegalArgumentException("O número de pessoas para divisão do pedido deve ser maior que zero");
        }

        order.markAsClosed();
        order.getTable().markAsAvailable();
        orderRepository.save(order);

        double discount = order.getDiscountPercentage();
        double total = order.getTotalPriceOfOrder();
        double totalWithDiscount = total *(1 - discount);
        return new CloseOrderResponse(
                orderId,
                order.getTable().getTableNumber(),
                order.getCreatedAt(),
                total,
                discount,
                totalWithDiscount,
                totalWithDiscount/numberOfPeople
        );

    }
}
