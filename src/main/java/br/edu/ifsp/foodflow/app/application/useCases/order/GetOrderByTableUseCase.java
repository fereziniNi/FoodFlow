package br.edu.ifsp.foodflow.app.application.useCases.order;

import br.edu.ifsp.foodflow.app.domain.order.Order;
import br.edu.ifsp.foodflow.app.domain.order.OrderRepository;
import br.edu.ifsp.foodflow.app.domain.order.dto.OrderDetailsDTO;
import br.edu.ifsp.foodflow.app.domain.orderItem.dto.OrderItemDetailsDTO;
import br.edu.ifsp.foodflow.app.domain.exceptions.OrderNotFoundException;
import br.edu.ifsp.foodflow.app.domain.table.Table;
import br.edu.ifsp.foodflow.app.domain.table.TableRepository;
import br.edu.ifsp.foodflow.app.infra.exceptions.TableNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class GetOrderByTableUseCase {
    private final TableRepository tableRepository;
    private final OrderRepository orderRepository;


    public GetOrderByTableUseCase(TableRepository tableRepository, OrderRepository orderRepository) {
        this.tableRepository = tableRepository;
        this.orderRepository = orderRepository;
    }


    public OrderDetailsDTO getOrderByTable(Integer tableId) {
        Objects.requireNonNull(tableId, "O Id da mesa é obrigatório.");

        Table table  = tableRepository.findByTableNumber(tableId)
                .orElseThrow(() -> new TableNotFoundException("Mesa não encontrada."));

        Order order = orderRepository.findActiveOrderByTable(table)
                .orElseThrow(() -> new OrderNotFoundException(
                        "Não existe comanda ativa para essa mesa."
                ));

        return new OrderDetailsDTO(
                order.getId(),
                order.getTable().getTableNumber(),
                order.getUser().getUsername(),
                order.getCreatedAt(),
                order.getActive(),
                order.getTotalPriceOfOrder(),
                order.getDiscountPercentage(),
                order.getOrderItems().stream()
                        .map(item -> new OrderItemDetailsDTO(
                                item.getId(),
                                item.getMenuItem().getName(),
                                item.getPrice(),
                                item.getStatus()
                        ))
                        .toList()
        );
    }
}
