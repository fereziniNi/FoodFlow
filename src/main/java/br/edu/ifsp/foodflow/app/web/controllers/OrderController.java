package br.edu.ifsp.foodflow.app.web.controllers;

import br.edu.ifsp.foodflow.app.application.useCases.order.AddItemToOrderUseCase;
import br.edu.ifsp.foodflow.app.domain.order.dto.AddItemToOrderDTO;
import br.edu.ifsp.foodflow.app.domain.order.dto.OrderResultDTO;
import br.edu.ifsp.foodflow.app.web.dtos.request.AddItemToOrderRequest;
import br.edu.ifsp.foodflow.app.web.dtos.response.OrderResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final AddItemToOrderUseCase addItemToOrderUseCase;

    public OrderController(AddItemToOrderUseCase addItemToOrderUseCase) {
        this.addItemToOrderUseCase = addItemToOrderUseCase;
    }

    @PostMapping("/{orderId}/items")
    public ResponseEntity<OrderResponse> addItem(@PathVariable UUID orderId, @Valid @RequestBody AddItemToOrderRequest request) {
        AddItemToOrderDTO command = new AddItemToOrderDTO(
                orderId,
                request.menuItemId(),
                request.observations(),
                request.addOnIds(),
                request.waiterId()
        );

        OrderResultDTO result = addItemToOrderUseCase.execute(command);
        OrderResponse response = new OrderResponse(
                result.orderId(),
                result.tableNumber(),
                result.createdAt(),
                result.active(),
                result.total()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}