package br.edu.ifsp.foodflow.app.domain.order;

import br.edu.ifsp.foodflow.app.domain.menuItem.MenuItemEntity;
import br.edu.ifsp.foodflow.app.domain.menuItem.MenuItemRepository;
import br.edu.ifsp.foodflow.app.domain.order.dto.AddItemToOrderRequest;
import br.edu.ifsp.foodflow.app.domain.order.dto.OrderResponse;
import br.edu.ifsp.foodflow.app.domain.order.useCases.AddItemToOrderUseCase;
import br.edu.ifsp.foodflow.app.domain.table.TableEntity;
import br.edu.ifsp.foodflow.app.domain.user.UserEntity;
import br.edu.ifsp.foodflow.app.domain.user.UserRepository;
import br.edu.ifsp.foodflow.app.infra.exceptions.OrderAlreadyClosedException;
import br.edu.ifsp.foodflow.app.infra.exceptions.UnavailableItemException;
import br.edu.ifsp.foodflow.app.infra.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@Tag("UnitTest")
@Tag("TDD")
@ExtendWith(MockitoExtension.class)
public class AddItemToOrderUseCaseTest {
    private UUID orderId;
    private UUID menuItemId;
    private UUID waiterId;

    @Mock private OrderRepository orderRepository;
    @Mock private MenuItemRepository menuItemRepository;
    @Mock private UserRepository userRepository;

    @InjectMocks private AddItemToOrderUseCase sut;

    @BeforeEach
    void setup(){
        orderId = UUID.randomUUID();
        menuItemId = UUID.randomUUID();
        waiterId = UUID.randomUUID();
    }

    @Test
    @DisplayName("Dado que o usuário esteja registrado e uma determinada mesa possua uma comanda ativa, " +
                 "quando o usuário adicionar um item à comanda, então o item deve ser registrado " +
                 "na comanda e o preço da comanda ser atualizado.")
    void shouldAddItemAnExistingOrder(){
        TableEntity table = new TableEntity(10);
        UserEntity userEntity = new UserEntity("Estrupicio", "Pereira", "estrupicio@gmail.com", "1234");
        OrderEntity orderEntity = new OrderEntity(table, userEntity);

        MenuItemEntity menuItemEntity = new MenuItemEntity(menuItemId, "X-Tudo", "Ingredientes", 40.0, 10);
        AddItemToOrderRequest request = new AddItemToOrderRequest(orderId, menuItemId, "Sem milho", null, waiterId);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(orderEntity));
        when(menuItemRepository.findById(menuItemId)).thenReturn(Optional.of(menuItemEntity));
        when(userRepository.findById(waiterId)).thenReturn(Optional.of(userEntity));

        assertThat(orderEntity.getTotalPriceOfOrder()).isEqualTo(0);

        OrderResponse response = sut.execute(request);

        assertThat(response).isNotNull();

        assertThat(orderEntity.getOrderItems().getFirst().getMenuItem().getName()).isEqualTo("X-Tudo");
        assertThat(orderEntity.getOrderItems().getFirst().getObservations()).isEqualTo("Sem milho");
        assertThat(orderEntity.getTotalPriceOfOrder()).isEqualTo(40);
        verify(orderRepository, times(1)).save(orderEntity);
    }

    @Test
    @DisplayName("Dado que o usuário não esteja registrado, quando tentar adicionar um item à alguma comanda, " +
                 "então a operação deve ser bloqueada e o sistema retornar erro de usuário não identificado.\n")
    void shouldThrowExceptionWhenWaiterIsNotRegistered() {
        AddItemToOrderRequest request = new AddItemToOrderRequest(orderId, menuItemId, "Sem milho", null, waiterId);

        OrderEntity mockOrder = mock(OrderEntity.class);
        when(mockOrder.getActive()).thenReturn(true);
        MenuItemEntity menuItemEntity = new MenuItemEntity(menuItemId, "X-Tudo", "Ingredientes", 40.0, 10);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));
        when(menuItemRepository.findById(menuItemId)).thenReturn(Optional.of(menuItemEntity));
        when(userRepository.findById(waiterId)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> sut.execute(request));

        assertEquals("O garçom informado não foi encontrado.", exception.getMessage());

        verify(orderRepository, never()).save(any());
        verify(mockOrder, never()).addOrderItem(any());
    }

    @Test
    @DisplayName("Dado que uma mesa possua uma comanda ativa e um item esteja marcado como indisponível, " +
                 "quando o usuário tentar adicionar esse item à comanda, então a operação deve falhar e " +
                 "o sistema informar a indisponibilidade do produto.")
    void shouldThrowExceptionWhenItemIsUnavailable(){
        AddItemToOrderRequest request = new AddItemToOrderRequest(orderId, menuItemId, "Sem milho", null, waiterId);

        OrderEntity mockOrder = mock(OrderEntity.class);
        when(mockOrder.getActive()).thenReturn(true);
        MenuItemEntity menuItemEntity = new MenuItemEntity(menuItemId, "X-Tudo", "Ingredientes", 40.0, 0);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));
        when(menuItemRepository.findById(menuItemId)).thenReturn(Optional.of(menuItemEntity));

        UnavailableItemException exception = assertThrows(UnavailableItemException.class, () -> sut.execute(request));
        assertEquals("O item solicitado encontra-se indisponível.", exception.getMessage());
    }

    @Test
    @DisplayName("Dado que a comanda informada seja nula, quando o usuário tentar adicionar um item, então o " +
                 "sistema deve disparar uma exceção informando que a comanda deve ser válida.")
    void shouldThrowExceptionWhenOrderIdIsNull(){
        AddItemToOrderRequest request = new AddItemToOrderRequest(null, menuItemId, "Sem milho", null, waiterId);
        NullPointerException exception = assertThrows(NullPointerException.class, () -> sut.execute(request));
        assertEquals("O ID do pedido não pode ser nulo", exception.getMessage());
    }

    @Test
    @DisplayName("Dado que a comanda informada seja inexistente, quando o usuário tentar adicionar um item, " +
                 "então o sistema deve disparar uma exceção informando que a comanda deve ser válida.")
    void shouldThrowExceptionWhenOrderIdNotExists(){
        AddItemToOrderRequest request = new AddItemToOrderRequest(orderId, menuItemId, "Sem milho", null, waiterId);
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> sut.execute(request));
        assertEquals("Pedido não encontrado para o ID: " + orderId, exception.getMessage());
    }

    @Test
    @DisplayName("Dado que a comanda informada esteja encerrada, quando o usuário tentar adicionar um item, " +
                 "então receberá uma informação de que é impossível adicionar um item a uma " +
                 "comanda encerrada e a operação não será concluída.")
    void shouldThrowExceptionWhenAddOrderItemToAFinishedOrder(){
        AddItemToOrderRequest request = new AddItemToOrderRequest(orderId, menuItemId, "Sem milho", null, waiterId);
        OrderEntity mockOrder = mock(OrderEntity.class);
        when(mockOrder.getActive()).thenReturn(false);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));
        OrderAlreadyClosedException exception = assertThrows(OrderAlreadyClosedException.class, () -> sut.execute(request));
        assertEquals("Pedido já finalizado para o ID: " + orderId, exception.getMessage());
    }
}