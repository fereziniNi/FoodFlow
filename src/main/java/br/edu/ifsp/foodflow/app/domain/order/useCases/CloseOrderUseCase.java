package br.edu.ifsp.foodflow.app.domain.order.useCases;

import java.util.Objects;
import java.util.UUID;

public class CloseOrderUseCase {

    public void closeOrder(UUID orderId, int numberOfPeople ){
        Objects.requireNonNull(orderId);
    }
}
