package br.edu.ifsp.foodflow.app.web.controllers;

import br.edu.ifsp.foodflow.app.application.useCases.order.*;
import br.edu.ifsp.foodflow.app.domain.order.Order;
import br.edu.ifsp.foodflow.app.domain.order.dto.*;
import br.edu.ifsp.foodflow.app.application.useCases.order.RemoveItemFromOrderUseCase;
import br.edu.ifsp.foodflow.app.web.dtos.request.AddItemToOrderRequest;
import br.edu.ifsp.foodflow.app.web.dtos.request.AdvanceOrderItemStatusRequest;
import br.edu.ifsp.foodflow.app.web.dtos.request.CloseOrderRequest;
import br.edu.ifsp.foodflow.app.web.dtos.request.OpenOrderRequest;
import br.edu.ifsp.foodflow.app.web.dtos.response.*;
import br.edu.ifsp.foodflow.app.web.dtos.request.RemoveItemFromOrderRequest;
import br.edu.ifsp.foodflow.app.web.dtos.response.AdvanceOrderItemStatusResponse;
import br.edu.ifsp.foodflow.app.web.dtos.response.CloseOrderResponse;
import br.edu.ifsp.foodflow.app.web.dtos.response.OrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@Tag(name = "Pedidos", description = "Gerenciamento de comandas e itens de pedido")
@SecurityRequirement(name = "bearer-jwt")
public class OrderController {
    private final AddItemToOrderUseCase addItemToOrderUseCase;
    private final CloseOrderUseCase closeOrderUseCase;
    private final AdvanceOrderItemStatusUseCase advanceOrderItemStatusUseCase;
    private final OpenTableOrderUseCase openTableOrderUseCase;
    private final GetOrderByTableUseCase getOrderByTableUseCase;
    private final RemoveItemFromOrderUseCase removeItemFromOrderUseCase;
    private final ListActiveOrdersUseCase listActiveOrdersUseCase;

    public OrderController(
            AddItemToOrderUseCase addItemToOrderUseCase,
            CloseOrderUseCase closeOrderUseCase,
            AdvanceOrderItemStatusUseCase advanceOrderItemStatusUseCase,
            OpenTableOrderUseCase openTableOrderUseCase,
            GetOrderByTableUseCase getOrderByTableUseCase,
            RemoveItemFromOrderUseCase removeItemFromOrderUseCase,
            ListActiveOrdersUseCase listActiveOrdersUseCase
    ) {
        this.addItemToOrderUseCase = addItemToOrderUseCase;
        this.closeOrderUseCase = closeOrderUseCase;
        this.advanceOrderItemStatusUseCase = advanceOrderItemStatusUseCase;
        this.openTableOrderUseCase = openTableOrderUseCase;
        this.getOrderByTableUseCase = getOrderByTableUseCase;
        this.removeItemFromOrderUseCase = removeItemFromOrderUseCase;
        this.listActiveOrdersUseCase = listActiveOrdersUseCase;
    }

    @Operation(summary = "Listar todas as comandas ativas")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de comandas ativas retornada com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<OrderDetailsResponse>> listActiveOrders() {
        List<OrderDetailsDTO> dtos = listActiveOrdersUseCase.execute();
        List<OrderDetailsResponse> response = dtos.stream()
                .map(dto -> new OrderDetailsResponse(
                        dto.orderId(),
                        dto.tableNumber(),
                        dto.userName(),
                        dto.createdAt(),
                        dto.active(),
                        dto.total(),
                        dto.discount(),
                        dto.items().stream()
                                .map(item -> new OrderItemDetailsResponse(
                                        item.id(),
                                        item.name(),
                                        item.observations(),
                                        item.price(),
                                        item.status(),
                                        item.waiterName()
                                ))
                                .toList()
                ))
                .toList();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Adicionar item ao pedido")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Item adicionado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição"),
        @ApiResponse(responseCode = "404", description = "Garçom não encontrado"),
        @ApiResponse(responseCode = "409", description = "Comanda já está fechada"),
        @ApiResponse(responseCode = "422", description = "Item indisponível no cardápio")
    })
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

    @Operation(summary = "Remover item do pedido")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Item removido com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição")
    })
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

    @Operation(summary = "Fechar comanda e calcular total")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Comanda fechada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Número de pessoas inválido"),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
        @ApiResponse(responseCode = "409", description = "Comanda já está fechada"),
        @ApiResponse(responseCode = "422", description = "Pedido sem itens")
    })
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

    @Operation(summary = "Avançar status de um item do pedido (PENDING → PREPARATION → FINISHED)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Status avançado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição"),
        @ApiResponse(responseCode = "404", description = "Pedido ou item não encontrado"),
        @ApiResponse(responseCode = "422", description = "Item já finalizado")
    })
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
        return  ResponseEntity.ok(response);
    }

    @Operation(summary = "Abrir nova comanda para uma mesa")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Comanda aberta com sucesso"),
        @ApiResponse(responseCode = "404", description = "Mesa ou usuário não encontrado"),
    })
    @PostMapping("/{tableId}/open")
    public ResponseEntity<OpenOrderResponse> openOrder(@PathVariable Integer tableId, @Valid  @RequestBody OpenOrderRequest request){
        Order order = openTableOrderUseCase.openOrder(
                tableId,
                request.userId()
        );

        OpenOrderResponse response = new OpenOrderResponse(
                order.getId(),
                order.getTable().getTableNumber(),
                order.getCreatedAt()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Buscar comanda ativa de uma mesa")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Detalhes da comanda retornados com sucesso"),
        @ApiResponse(responseCode = "404", description = "Nenhuma comanda ativa para esta mesa")
    })
    @GetMapping("/tables/{tableId}/order")
    public ResponseEntity<OrderDetailsResponse> getOrderByTable(
            @PathVariable Integer tableId
    ) {

        OrderDetailsDTO dto = getOrderByTableUseCase.getOrderByTable(tableId);

        OrderDetailsResponse response = new OrderDetailsResponse(
                dto.orderId(),
                dto.tableNumber(),
                dto.userName(),
                dto.createdAt(),
                dto.active(),
                dto.total(),
                dto.discount(),
                dto.items().stream()
                        .map(item -> new OrderItemDetailsResponse(
                                item.id(),
                                item.name(),
                                item.observations(),
                                item.price(),
                                item.status(),
                                item.waiterName()
                        ))
                        .toList()
        );

        return ResponseEntity.ok(response);
    }
}
