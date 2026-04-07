package br.edu.ifsp.foodflow.app.domain.order.useCases;

import br.edu.ifsp.foodflow.app.domain.order.OrderEntity;
import br.edu.ifsp.foodflow.app.domain.order.OrderRepository;
import br.edu.ifsp.foodflow.app.domain.order.dto.CloseOrderResponse;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

public class CloseOrderUseCase {

    private final OrderRepository orderRepository;

    public CloseOrderUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public CloseOrderResponse closeOrder(UUID orderId, int numberOfPeople ){
        Objects.requireNonNull(orderId,"O ID do pedido não pode ser nulo");

        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("Pedido não encontrado para o ID:" + orderId));
        if(!order.getActive()){
            throw new IllegalStateException("Pedido já finalizado para o ID:"+ orderId);
        }
        if(order.getOrderItems().isEmpty()){
            throw new IllegalStateException("Pedido sem itens não pode ser finalizado para o ID:"+ orderId);
        }

        if(numberOfPeople < 1) {
            throw new IllegalArgumentException("O número de pessoas para divisão do pedido deve ser maior que zero");
        }

        order.markAsClosed();
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
