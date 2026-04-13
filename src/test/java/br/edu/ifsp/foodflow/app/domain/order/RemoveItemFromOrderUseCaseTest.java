package br.edu.ifsp.foodflow.app.domain.order;

import br.edu.ifsp.foodflow.app.domain.addOn.AddOn;
import br.edu.ifsp.foodflow.app.domain.menuItem.MenuItem;
import br.edu.ifsp.foodflow.app.web.dtos.response.OrderResponse;
import br.edu.ifsp.foodflow.app.domain.order.dto.RemoveItemFromOrderRequest;
import br.edu.ifsp.foodflow.app.application.useCases.order.RemoveItemFromOrderUseCase;
import br.edu.ifsp.foodflow.app.domain.orderItem.OrderItem;
import br.edu.ifsp.foodflow.app.domain.orderItem.OrderItemRepository;
import br.edu.ifsp.foodflow.app.domain.orderItem.OrderItemStatus;
import br.edu.ifsp.foodflow.app.domain.table.Table;
import br.edu.ifsp.foodflow.app.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("UnitTest")
@Tag("TDD")
@ExtendWith(MockitoExtension.class)
public class RemoveItemFromOrderUseCaseTest {
    private UUID orderId;
    private UUID orderItemId;
    private UUID otherOrderItemId;

    @Mock private OrderRepository orderRepository;
    @Mock private OrderItemRepository orderItemRepository;
    @InjectMocks private RemoveItemFromOrderUseCase sut;

    @BeforeEach
    void setup(){
        orderId = UUID.randomUUID();
        orderItemId = UUID.randomUUID();
        otherOrderItemId = UUID.randomUUID();
    }

    @Test
    @DisplayName("Dado que o usuário esteja registrado e uma mesa possua uma comanda ativa e possua um item adicionado, " +
                 "quando o garçom solicitar a remoção do item, então o item deve ser removido da comanda.")
    void shouldRemoveItemFromOrder(){
        MenuItem xBurguer = new MenuItem(UUID.randomUUID(), "X-Burguer", "Delicioso", 20.0, 10);
        MenuItem xTudo = new MenuItem(UUID.randomUUID(), "X-Tudo", "Monstro", 35.0, 5);

        OrderItem itemXburguer = new OrderItem(orderItemId, xBurguer, new ArrayList<>(), null, "");
        OrderItem itemXtudo = new OrderItem(otherOrderItemId, xTudo, new ArrayList<>(), null, "");

        List<OrderItem> initialItems = new ArrayList<>(List.of(itemXtudo, itemXburguer));

        Table table = new Table(10);
        User waiter = mock(User.class);

        Order order = new Order(orderId, table, initialItems, LocalDateTime.now(), true, waiter);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderItemRepository.findById(orderItemId)).thenReturn(Optional.of(itemXburguer));

        assertEquals(2, order.getOrderItems().size());
        assertEquals(55, order.getTotalPriceOfOrder());

        sut.execute(new RemoveItemFromOrderRequest(orderId, orderItemId));

        assertEquals(1, order.getOrderItems().size());
        assertEquals(35, order.getTotalPriceOfOrder());

        assertFalse(order.getOrderItems().stream()
                .anyMatch(item -> item.getMenuItem().getId().equals(orderItemId)));

        verify(orderRepository).save(order);
    }

    @Test
    @DisplayName("Dado que um item da comanda esteja marcado como 'em preparo', quando o usuário tentar remover, " +
                 "o sistema deve bloquear a remoção ou solicitar uma permissão extra para remover.")
    void shouldThrowExceptionWhenRemovingItemInPreparation(){
        MenuItem xBurguer = new MenuItem(UUID.randomUUID(), "X-Burguer", "Delicioso", 20.0, 10);

        OrderItem itemInPreparation = new OrderItem(orderItemId, xBurguer, new ArrayList<>(), null, "");
        itemInPreparation.upgradeProgress();

        Table table = new Table(10);
        User waiter = mock(User.class);

        List<OrderItem> initialItems = new ArrayList<>(List.of(itemInPreparation));
        Order order = new Order(orderId, table, initialItems, LocalDateTime.now(), true, waiter);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderItemRepository.findById(orderItemId)).thenReturn(Optional.of(itemInPreparation));

        RemoveItemFromOrderRequest request = new RemoveItemFromOrderRequest(orderId, orderItemId);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            sut.execute(request);
        });

        assertThat(itemInPreparation.getStatus()).isEqualTo(OrderItemStatus.PREPARATION);
        assertEquals("Não é possível remover um item que já está em preparo ou finalizado.", exception.getMessage());

        verify(orderRepository, never()).save(any());
    }

    @Test
    @DisplayName("Dado que a comanda de uma mesa tenha um total pré-calculado, quando o usuário remover um item, " +
                 "então o preço da comanda é atualizado para o novo total.")
    void shouldUpdateOrderTotalPriceWhenItemIsRemoved() {
        MenuItem xBurguer = new MenuItem(UUID.randomUUID(), "X-Burguer", "Delicioso", 20.0, 10);
        MenuItem xTudo = new MenuItem(UUID.randomUUID(), "X-Tudo", "Monstro", 35.0, 5);

        OrderItem itemToRemove = new OrderItem(orderItemId, xBurguer, new ArrayList<>(), null, "");
        OrderItem itemToKeep = new OrderItem(otherOrderItemId, xTudo, new ArrayList<>(), null, "");

        List<OrderItem> initialItems = new ArrayList<>(List.of(itemToRemove, itemToKeep));

        Table table = new Table(10);
        User waiter = mock(User.class);

        Order order = new Order(orderId, table, initialItems, LocalDateTime.now(), true, waiter);
        assertEquals(55.0, order.getTotalPriceOfOrder(), "O total inicial da comanda deveria ser 55.0");

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderItemRepository.findById(orderItemId)).thenReturn(Optional.of(itemToRemove));

        RemoveItemFromOrderRequest request = new RemoveItemFromOrderRequest(orderId, orderItemId);
        OrderResponse response = sut.execute(request);

        assertEquals(35.0, response.total(), "O preço na resposta do UseCase deve ser atualizado para 35.0");
        assertEquals(35.0, order.getTotalPriceOfOrder(), "O preço interno da entidade deve ser atualizado para 35.0");
        assertEquals(1, order.getOrderItems().size());
    }

    @Test
    @DisplayName("Dado que o ID da comanda no request é nulo, quando o usuário tentar remover um item, " +
                 "então o sistema deve lançar uma IllegalArgumentException.")
    void shouldThrowExceptionWhenOrderIdIsNull() {
        RemoveItemFromOrderRequest request = new RemoveItemFromOrderRequest(null, orderItemId);

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            sut.execute(request);
        });

        assertEquals("O ID da comanda é obrigatório e deve ser válido.", exception.getMessage());

        verifyNoInteractions(orderRepository);
        verifyNoInteractions(orderItemRepository);
    }

    @Test
    @DisplayName("Dado que a comanda esteja encerrada, quando o usuário tentar remover um item, " +
                 "então o sistema deve bloquear a ação lançando uma exceção.")
    void shouldThrowExceptionWhenRemovingItemFromClosedOrder() {
        MenuItem xBurguer = new MenuItem(UUID.randomUUID(), "X-Burguer", "Delicioso", 20.0, 10);
        OrderItem itemToRemove = new OrderItem(orderItemId, xBurguer, new ArrayList<>(), null, "");

        List<OrderItem> initialItems = new ArrayList<>(List.of(itemToRemove));
        Table table = new Table(10);
        User waiter = mock(User.class);

        Order closedOrder = new Order(orderId, table, initialItems, LocalDateTime.now(), false, waiter);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(closedOrder));
        when(orderItemRepository.findById(orderItemId)).thenReturn(Optional.of(itemToRemove));

        RemoveItemFromOrderRequest request = new RemoveItemFromOrderRequest(orderId, orderItemId);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            sut.execute(request);
        });

        assertEquals("Não é possível alterar uma comanda já encerrada.", exception.getMessage());
        verify(orderRepository, never()).save(any());
    }

    @Test
    @DisplayName("Dado que um item possua adicionais, quando o usuário remover o item, " +
            "então o valor do item e dos adicionais deve ser subtraído do total da comanda.")
    void shouldUpdateTotalIncludingAddOnsWhenItemIsRemoved() {
        AddOn extraCheese = new AddOn(UUID.randomUUID(), "Queijo Extra", 5.0);

        MenuItem xBurguer = new MenuItem(UUID.randomUUID(), "X-Burguer", "Base", 20.0, 10);

        OrderItem itemWithAddOn = new OrderItem(
                orderItemId,
                xBurguer,
                new ArrayList<>(List.of(extraCheese)),
                null,
                ""
        );

        List<OrderItem> items = new ArrayList<>(List.of(itemWithAddOn));
        Order order = new Order(orderId, new Table(1), items, LocalDateTime.now(), true, mock(User.class));

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderItemRepository.findById(orderItemId)).thenReturn(Optional.of(itemWithAddOn));

        sut.execute(new RemoveItemFromOrderRequest(orderId, orderItemId));

        assertEquals(0.0, order.getTotalPriceOfOrder(), "O total da comanda deveria ser 0.0 após remover o item e seus adicionais.");
    }
}
