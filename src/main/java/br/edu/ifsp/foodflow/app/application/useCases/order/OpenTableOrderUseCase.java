package br.edu.ifsp.foodflow.app.application.useCases.order;

import br.edu.ifsp.foodflow.app.domain.order.Order;
import br.edu.ifsp.foodflow.app.domain.order.OrderRepository;
import br.edu.ifsp.foodflow.app.domain.table.Table;
import br.edu.ifsp.foodflow.app.domain.table.TableRepository;
import br.edu.ifsp.foodflow.app.domain.user.User;
import br.edu.ifsp.foodflow.app.domain.user.UserRepository;
import br.edu.ifsp.foodflow.app.domain.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class OpenTableOrderUseCase {
    private final TableRepository tableRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OpenTableOrderUseCase(TableRepository tableRepository, OrderRepository orderRepository, UserRepository userRepository) {
        this.tableRepository = tableRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public Order openOrder(Integer tableId, UUID userId){
        Objects.requireNonNull(tableId,"O ID da mesa é obrigatório.");
        if (tableId < 1) throw new IllegalArgumentException("O ID da mesa deve ser positivo.");
        Objects.requireNonNull(userId,"O ID do usuário é obrigatório.");

        Table table = tableRepository.findByTableNumber(tableId)
                .orElseThrow(() -> new IllegalArgumentException("Mesa não encontrada."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        orderRepository.findActiveOrderByTable(table).ifPresent(order -> {
            throw new IllegalStateException("Já existe uma comanda ativa para esta mesa.");
        });

        Order newOrder = new Order(table, user);
        orderRepository.save(newOrder);
        return newOrder;
    }
}
