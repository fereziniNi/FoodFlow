package br.edu.ifsp.foodflow.app.domain.order;

import br.edu.ifsp.foodflow.app.domain.menuItem.MenuItemEntity;
import br.edu.ifsp.foodflow.app.domain.menuItem.MenuItemRepository;
import br.edu.ifsp.foodflow.app.domain.orderItem.OrderItemEntity;
import br.edu.ifsp.foodflow.app.domain.orderItem.OrderItemStatus;
import br.edu.ifsp.foodflow.app.domain.table.TableEntity;
import br.edu.ifsp.foodflow.app.domain.user.UserEntity;
import br.edu.ifsp.foodflow.app.domain.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

public class RemoveItemFromOrderUseCaseTest {
    private UUID orderId;
    private UUID menuItemId;
    private UUID otherMenuItemId;

    @Mock private OrderRepository orderRepository;
    @Mock private MenuItemRepository menuItemRepository;
    @Mock private UserRepository userRepository;
    @InjectMocks private RemoveItemFromOrderUseCase sut;

    @BeforeEach
    void setup(){
        orderId = UUID.randomUUID();
        menuItemId = UUID.randomUUID();
        otherMenuItemId = UUID.randomUUID();
    }

    @Test
    @DisplayName("Dado que o usuário esteja registrado e uma mesa possua uma comanda ativa e possua um item adicionado, " +
                 "quando o garçom solicitar a remoção do item, então o item deve ser removido da comanda.")
    void shouldRemoveItemFromOrder(){
        MenuItemEntity xBurguer = new MenuItemEntity(menuItemId, "X-Burguer", "Delicioso", 20.0, 10);
        MenuItemEntity xTudo = new MenuItemEntity(otherMenuItemId, "X-Tudo", "Monstro", 35.0, 5);

        OrderItemEntity itemXburguer = new OrderItemEntity(UUID.randomUUID(), xBurguer, new ArrayList<>(), null, "", 20.0);
        OrderItemEntity itemXtudo = new OrderItemEntity(UUID.randomUUID(), xTudo, new ArrayList<>(), null, "", 35.0);

        // Lista mutável para o pedido (List.of() cria uma lista imutável, o que faria o remove() falhar)
        List<OrderItemEntity> initialItems = new ArrayList<>(List.of(itemXtudo, itemXburguer));

        TableEntity table = new TableEntity(10);
        UserEntity waiter = mock(UserEntity.class);

        OrderEntity order = new OrderEntity(orderId, table, initialItems, LocalDateTime.now(), true, waiter);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        sut.execute(orderId, menuItemIdToRemove);

        assertEquals(1, order.getOrderItems().size());
        assertFalse(order.getOrderItems().stream()
                .anyMatch(item -> item.getMenuItem().getId().equals(menuItemId)));

        verify(orderRepository).save(order);
    }
}
