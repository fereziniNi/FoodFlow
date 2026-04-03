package br.edu.ifsp.foodflow.app.service;

import br.edu.ifsp.foodflow.app.domain.order.OrderEntity;

public class OpenTableOrderService {

    public OrderEntity openOrder(Integer tableId){
        if (tableId == null) {
            throw new IllegalArgumentException("O ID da mesa é obrigatório.");
        }
        return null;
    }
}
