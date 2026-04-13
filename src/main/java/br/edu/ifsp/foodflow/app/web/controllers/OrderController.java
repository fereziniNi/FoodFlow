package br.edu.ifsp.foodflow.app.web.controllers;

import br.edu.ifsp.foodflow.app.application.useCases.order.AddItemToOrderUseCase;
import br.edu.ifsp.foodflow.app.application.useCases.order.AdvanceOrderItemStatusUseCase;
import br.edu.ifsp.foodflow.app.application.useCases.order.CloseOrderUseCase;
import br.edu.ifsp.foodflow.app.application.useCases.order.RemoveItemFromOrderUseCase;
import br.edu.ifsp.foodflow.app.domain.order.dto.*;
import br.edu.ifsp.foodflow.app.web.dtos.request.AddItemToOrderRequest;
import br.edu.ifsp.foodflow.app.web.dtos.request.AdvanceOrderItemStatusRequest;
import br.edu.ifsp.foodflow.app.web.dtos.request.CloseOrderRequest;
import br.edu.ifsp.foodflow.app.web.dtos.request.RemoveItemFromOrderRequest;
import br.edu.ifsp.foodflow.app.web.dtos.response.AdvanceOrderItemStatusResponse;
import br.edu.ifsp.foodflow.app.web.dtos.response.CloseOrderResponse;
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
    private final CloseOrderUseCase closeOrderUseCase;
    private final AdvanceOrderItemStatusUseCase advanceOrderItemStatusUseCase;
    private final RemoveItemFromOrderUseCase removeItemFromOrderUseCase;

    public OrderController(AddItemToOrderUseCase addItemToOrderUseCase, CloseOrderUseCase closeOrderUseCase, AdvanceOrderItemStatusUseCase advanceOrderItemStatusUseCase, RemoveItemFromOrderUseCase removeItemFromOrderUseCase) {
        this.addItemToOrderUseCase = addItemToOrderUseCase;
        this.closeOrderUseCase = closeOrderUseCase;
        this.advanceOrderItemStatusUseCase = advanceOrderItemStatusUseCase;
        this.removeItemFromOrderUseCase = removeItemFromOrderUseCase;
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

    @DeleteMapping("/{orderId}/items")
    public ResponseEntity<OrderResponse> removeItem(@PathVariable UUID orderId, @Valid @RequestBody RemoveItemFromOrderRequest request) {
        RemoveItemFromOrderDTO command = new RemoveItemFromOrderDTO(orderId, request.orderItemId());

        OrderResultDTO result = removeItemFromOrderUseCase.execute(command);

        OrderResponse response = new OrderResponse(
                result.orderId(),
                result.tableNumber(),
                result.createdAt(),
                result.active(),
                result.total()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{orderId}/close")
    public ResponseEntity<CloseOrderResponse> closeOrder(@PathVariable UUID orderId,  @Valid  @RequestBody CloseOrderRequest request){
        CloseOrderResultDTO result = closeOrderUseCase.closeOrder(orderId, request.numberOfPeople());
        CloseOrderResponse response = new CloseOrderResponse(
                result.orderId(),
                result.tableNumber(),
                result.createdAt(),
                result.totalWithoutDiscount(),
                result.discountPercentage(),
                result.totalWithDiscount(),
                result.totalPerPerson()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{orderId}/advance-status")
    public ResponseEntity<AdvanceOrderItemStatusResponse> advanceOrderItemStatus(@PathVariable UUID orderId, @Valid @RequestBody AdvanceOrderItemStatusRequest request){
        AdvanceOrderItemStatusDTO result = advanceOrderItemStatusUseCase.advanceStatus(orderId,request.itemId());
        AdvanceOrderItemStatusResponse response = new AdvanceOrderItemStatusResponse(
               result.itemId(),
               result.orderId(),
               result.status(),
               result.createdAt(),
               result.updatedAt()

        );
        return ResponseEntity.ok(response);
    }
}