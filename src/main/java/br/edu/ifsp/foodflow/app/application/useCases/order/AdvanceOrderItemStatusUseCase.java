package br.edu.ifsp.foodflow.app.application.useCases.order;

import java.util.Objects;
import java.util.UUID;

public class AdvanceOrderItemStatusUseCase {
    public void advanceStatus(UUID orderId, UUID itemId){
        Objects.requireNonNull(orderId,"O ID do pedido não pode ser nulo");
        Objects.requireNonNull(itemId,"O ID do item não pode ser nulo");
    }
}
