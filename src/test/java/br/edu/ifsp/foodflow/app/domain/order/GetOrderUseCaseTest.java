package br.edu.ifsp.foodflow.app.domain.order;

import br.edu.ifsp.foodflow.app.domain.menuItem.MenuItem;
import br.edu.ifsp.foodflow.app.domain.order.dto.OrderDetailsResponse;
import br.edu.ifsp.foodflow.app.application.useCases.order.GetOrderUseCase;
import br.edu.ifsp.foodflow.app.domain.orderItem.OrderItem;
import br.edu.ifsp.foodflow.app.domain.table.Table;
import br.edu.ifsp.foodflow.app.domain.user.User;
import br.edu.ifsp.foodflow.app.infra.exceptions.OrderNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetOrderUseCaseTest {
    @InjectMocks
    private GetOrderUseCase service;

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

        OrderNotFoundException exception = assertThrows(OrderNotFoundException.class,
                () -> service.getOrderById(notExistId));

        assertEquals("Pedido não encontrado.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve retornar OrderDetailsResponse com itens quando houver pedido ativo para a mesa")
    void shouldReturnOrderDTOWhenActiveOrderExists() {
        UUID orderId = UUID.randomUUID();

        Table table = new Table(1);
        User user = new User("João Silva", "João", "joao@gmail.com", "1234");

        Order order = new Order(table, user);

        MenuItem menuItem = new MenuItem(UUID.randomUUID(), "Prato", "Descrição do prato", 50.0, 10);
        OrderItem item = new OrderItem(UUID.randomUUID(), menuItem, List.of(), user, "");

        order.addOrderItem(item);
        order.addOrderItem(item);

        when(orderRepository.findById(orderId))
                .thenReturn(Optional.of(order));

        OrderDetailsResponse result = service.getOrderById(orderId);

        assertNotNull(result);
        assertEquals(order.getId(), result.orderId());
        assertEquals(table.getTableNumber(), result.tableNumber());
        assertEquals(user.getName(), result.userName());
        assertEquals(2, result.items().size());
        assertEquals(50.0, result.items().get(0).price());
        assertEquals(100.0, result.total());
    }

    @Test
    @DisplayName("Deve retornar OrderDetailsResponse com lista de itens vazia quando o pedido não tiver itens")
    void shouldReturnEmptyItemsWhenOrderHasNoItems() {
        UUID orderId = UUID.randomUUID();

        Table table = new Table(1);
        User user = new User("João Silva", "João", "joao@gmail.com", "1234");

        Order order = new Order(table, user);

        when(orderRepository.findById(orderId))
                .thenReturn(Optional.of(order));

        OrderDetailsResponse result = service.getOrderById(orderId);

        assertNotNull(result);
        assertEquals(order.getId(), result.orderId());
        assertEquals(table.getTableNumber(), result.tableNumber());
        assertEquals(user.getName(), result.userName());
        assertNotNull(result.items());
        assertTrue(result.items().isEmpty());
        assertEquals(0.0, result.total());
    }

    @Test
    @DisplayName("Deve aplicar desconto correto no OrderDetailsResponse")
    void shouldApplyDiscountCorrectly() {
        UUID orderId = UUID.randomUUID();

        Table table = new Table(1);
        User user = new User("João Silva", "João", "joao@gmail.com", "1234");

        Order order = new Order(table, user);

        MenuItem menuItem1 = new MenuItem(UUID.randomUUID(), "Prato 1", "desc", 120.0, 10);
        OrderItem item1 = new OrderItem(UUID.randomUUID(), menuItem1, List.of(), user, "");

        order.addOrderItem(item1);

        when(orderRepository.findById(orderId))
                .thenReturn(Optional.of(order));

        OrderDetailsResponse result = service.getOrderById(orderId);

        assertEquals(120.0, result.total());
        assertEquals(0.05, result.discount());
    }



}