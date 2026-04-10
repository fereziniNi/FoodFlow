package br.edu.ifsp.foodflow.app.application.useCases.order;

import br.edu.ifsp.foodflow.app.domain.order.Order;
import br.edu.ifsp.foodflow.app.domain.order.OrderRepository;
import br.edu.ifsp.foodflow.app.domain.order.dto.OrderDetailsResponse;
import br.edu.ifsp.foodflow.app.domain.orderItem.dto.OrderItemDetailsResponse;
import br.edu.ifsp.foodflow.app.domain.exceptions.OrderNotFoundException;
import br.edu.ifsp.foodflow.app.domain.table.Table;
import br.edu.ifsp.foodflow.app.domain.table.TableRepository;
import br.edu.ifsp.foodflow.app.infra.exceptions.TableNotFoundException;

import java.util.Objects;

public class GetOrderByTableUseCase {
    private final TableRepository tableRepository;
    private final OrderRepository orderRepository;


    public GetOrderByTableUseCase(TableRepository tableRepository, OrderRepository orderRepository) {
        this.tableRepository = tableRepository;
        this.orderRepository = orderRepository;
    }


    public OrderDetailsResponse getOrderByTable(Integer tableId) {
        Objects.requireNonNull(tableId, "O Id da mesa é obrigatório.");

        Table table  = tableRepository.findByTableNumber(tableId)
                .orElseThrow(() -> new TableNotFoundException("Mesa não encontrada."));

        Order order = orderRepository.findActiveOrderByTable(table)
                .orElseThrow(() -> new OrderNotFoundException(
                        "Não existe comanda ativa para essa mesa."
                ));

        return new OrderDetailsResponse(
                order.getId(),
                order.getTable().getTableNumber(),
                order.getUser().getUsername(),
                order.getCreatedAt(),
                order.getActive(),
                order.getTotalPriceOfOrder(),
                order.getDiscountPercentage(),
                order.getOrderItems().stream().map(
                        item->new OrderItemDetailsResponse(
                                item.getId(),
                                item.getObservations(),
                                item.getPrice()
                )).toList()
        );
    }
}
