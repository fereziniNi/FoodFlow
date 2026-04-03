package br.edu.ifsp.foodflow.app.service;

import br.edu.ifsp.foodflow.app.domain.order.OrderEntity;
import br.edu.ifsp.foodflow.app.domain.order.OrderRepository;
import br.edu.ifsp.foodflow.app.domain.table.TableEntity;
import br.edu.ifsp.foodflow.app.domain.table.TableRepository;
import org.hibernate.query.Order;

public class OpenTableOrderService {
    private final TableRepository tableRepository;
    private final OrderRepository orderRepository;

    public OpenTableOrderService(TableRepository tableRepository, OrderRepository orderRepository) {
        this.tableRepository = tableRepository;
        this.orderRepository = orderRepository;
    }

    public OrderEntity openOrder(Integer tableId){
        if (tableId == null) {
            throw new IllegalArgumentException("O ID da mesa é obrigatório.");
        }
        TableEntity table = tableRepository.findById(tableId)
                .orElseThrow(() -> new IllegalArgumentException("Mesa não encontrada."));

        OrderEntity newOrder = new OrderEntity(table);
        orderRepository.save(newOrder);
        return newOrder;
    }
}
