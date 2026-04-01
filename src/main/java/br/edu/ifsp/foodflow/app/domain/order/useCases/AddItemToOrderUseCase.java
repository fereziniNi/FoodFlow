package br.edu.ifsp.foodflow.app.domain.order.useCases;

import br.edu.ifsp.foodflow.app.domain.order.dto.AddItemToOrderRequest;
import br.edu.ifsp.foodflow.app.domain.order.dto.OrderResponse;

import java.util.UUID;

public class AddItemToOrderUseCase {
    public OrderResponse execute(UUID orderUUID, AddItemToOrderRequest dto){
        return null;
    }
}
