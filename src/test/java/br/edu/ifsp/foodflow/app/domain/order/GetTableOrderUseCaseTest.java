package br.edu.ifsp.foodflow.app.domain.order;

import br.edu.ifsp.foodflow.app.domain.menuItem.MenuItemEntity;
import br.edu.ifsp.foodflow.app.domain.order.dto.OrderDTO;
import br.edu.ifsp.foodflow.app.domain.order.mapper.OrderMapper;
import br.edu.ifsp.foodflow.app.domain.order.useCases.GetTableOrderUseCase;
import br.edu.ifsp.foodflow.app.domain.orderItem.OrderItemEntity;
import br.edu.ifsp.foodflow.app.domain.table.TableEntity;
import br.edu.ifsp.foodflow.app.domain.user.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetTableOrderUseCaseTest {
    @InjectMocks
    private GetTableOrderUseCase service;

    @Mock
    private OrderRepository orderRepository;

    UUID notExistId = UUID.randomUUID();

    @Test
    @DisplayName("Deve lançar IllegalArgumentException quando o ID do pedido for nulo")
    void shouldThrowExceptionWhenOrderIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> service.getOrderById(null));

        assertEquals("O Id do pedido é obrigatório.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException quando a pedido não existir")
    void shouldThrowExceptionWhenOrderDoesNotExist() {
        when(orderRepository.findById(notExistId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> service.getOrderById(notExistId));

        assertEquals("Pedido não encontrado.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve retornar OrderDTO com itens quando houver pedido ativo para a mesa")
    void shouldReturnOrderDTOWhenActiveOrderExists() {
        UUID orderId = UUID.randomUUID();

        TableEntity table = new TableEntity(1);
        UserEntity user = new UserEntity("João Silva", "João", "joao@gmail.com", "1234");

        OrderEntity order = new OrderEntity(table, user);

        MenuItemEntity menuItem = new MenuItemEntity(UUID.randomUUID(), "Prato", "Descrição do prato", 50.0, 10);
        OrderItemEntity item = new OrderItemEntity(UUID.randomUUID(), menuItem, List.of(), user, "");

        order.addOrderItem(item);
        order.addOrderItem(item);

        when(orderRepository.findById(orderId))
                .thenReturn(Optional.of(order));

        OrderDTO result = service.getOrderById(orderId);

        assertNotNull(result);
        assertEquals(order.getId(), result.getOrderId());
        assertEquals(table.getTableNumber(), result.getTableNumber());
        assertEquals(user.getName(), result.getUserName());
        assertEquals(2, result.getItems().size());
        assertEquals(50.0, result.getItems().get(0).getPrice());
        assertEquals(100.0, result.getTotal());
    }

}