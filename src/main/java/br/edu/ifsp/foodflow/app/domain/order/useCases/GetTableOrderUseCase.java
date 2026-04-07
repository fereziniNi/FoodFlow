package br.edu.ifsp.foodflow.app.domain.order.useCases;

import br.edu.ifsp.foodflow.app.domain.order.OrderEntity;

import java.util.UUID;

public class GetTableOrderUseCase {

    public OrderEntity getOrderById(UUID orderId){
        if(orderId == null) throw new IllegalArgumentException("O ID da mesa é obrigatório.");
        return null;
    }
}
