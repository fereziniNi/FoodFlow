package br.edu.ifsp.foodflow.app.domain.order.useCases;

import br.edu.ifsp.foodflow.app.domain.order.OrderEntity;
import br.edu.ifsp.foodflow.app.domain.order.OrderRepository;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CloseOrderUseCase {

    private final OrderRepository orderRepository;

    public CloseOrderUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void closeOrder(UUID orderId, int numberOfPeople ){
        Objects.requireNonNull(orderId,"O ID do pedido não pode ser nulo");

        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("Pedido não encontrado para o ID:" + orderId));
        if(!order.getActive()){
            throw new IllegalStateException("Pedido já finalizado para o ID:"+ orderId);
        }

        if(numberOfPeople < 1) {
            throw new IllegalArgumentException("O número de pessoas para divisão do pedido deve ser maior que zero");
        }

    }
}
