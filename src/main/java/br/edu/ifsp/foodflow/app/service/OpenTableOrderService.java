package br.edu.ifsp.foodflow.app.service;

import br.edu.ifsp.foodflow.app.domain.order.OrderEntity;
import br.edu.ifsp.foodflow.app.domain.order.OrderRepository;
import br.edu.ifsp.foodflow.app.domain.table.TableEntity;
import br.edu.ifsp.foodflow.app.domain.table.TableRepository;
import br.edu.ifsp.foodflow.app.domain.user.UserEntity;
import br.edu.ifsp.foodflow.app.domain.user.UserRepository;
import org.hibernate.query.Order;

import java.util.UUID;

public class OpenTableOrderService {
    private final TableRepository tableRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OpenTableOrderService(TableRepository tableRepository, OrderRepository orderRepository, UserRepository userRepository) {
        this.tableRepository = tableRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public OrderEntity openOrder(Integer tableId, UUID userId){
        if (tableId == null)throw new IllegalArgumentException("O ID da mesa é obrigatório.");
        if (userId == null) throw new IllegalStateException("O ID do usuário é obrigatório.");

        TableEntity table = tableRepository.findById(tableId)
                .orElseThrow(() -> new IllegalArgumentException("Mesa não encontrada."));

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        orderRepository.findActiveOrderByTable(table).ifPresent(order -> {
            throw new IllegalStateException("Já existe uma comanda ativa para esta mesa.");
        });

        OrderEntity newOrder = new OrderEntity(table, user);
        orderRepository.save(newOrder);
        return newOrder;
    }
}
