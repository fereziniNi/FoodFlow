package br.edu.ifsp.foodflow.app.domain.order;

import br.edu.ifsp.foodflow.app.domain.menuItem.MenuItemEntity;
import br.edu.ifsp.foodflow.app.domain.menuItem.MenuItemRepository;
import br.edu.ifsp.foodflow.app.domain.order.dto.AddItemToOrderRequest;
import br.edu.ifsp.foodflow.app.domain.order.dto.OrderResponse;
import br.edu.ifsp.foodflow.app.domain.order.useCases.AddItemToOrderUseCase;
import br.edu.ifsp.foodflow.app.domain.table.TableEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddItemToOrderUseCaseTest {
    @Mock private OrderRepository orderRepository;
    @Mock private MenuItemRepository menuItemRepository;
    @InjectMocks private AddItemToOrderUseCase sut;

    @Test
    void shouldAddItemAnExistingOrder(){
        UUID orderId = UUID.randomUUID();
        UUID menuItemId = UUID.randomUUID();

        TableEntity table = new TableEntity(10);
        OrderEntity orderEntity = new OrderEntity(table);

        MenuItemEntity menuItemEntity = new MenuItemEntity(menuItemId, "X-Tudo", "Ingredientes", 40.0);
        AddItemToOrderRequest request = new AddItemToOrderRequest(menuItemId, "Sem milho", null, UUID.randomUUID());

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(orderEntity));
        when(menuItemRepository.findById(menuItemId)).thenReturn(Optional.of(menuItemEntity));

        OrderResponse response = sut.execute(orderId, request);

        assertThat(response).isNotNull();

        assertThat(orderEntity.getOrderItems().getFirst().getMenuItem().getName()).isEqualTo("X-Tudo");
        assertThat(orderEntity.getOrderItems().getFirst().getObservations()).isEqualTo("Sem milho");

        verify(orderRepository, times(1)).save(orderEntity);
    }
}
