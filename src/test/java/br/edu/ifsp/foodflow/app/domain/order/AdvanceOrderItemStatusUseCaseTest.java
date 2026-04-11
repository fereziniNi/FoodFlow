package br.edu.ifsp.foodflow.app.domain.order;


import br.edu.ifsp.foodflow.app.application.useCases.order.AdvanceOrderItemStatusUseCase;
import br.edu.ifsp.foodflow.app.domain.table.Table;
import br.edu.ifsp.foodflow.app.domain.user.User;
import br.edu.ifsp.foodflow.app.infra.exceptions.OrderItemNotFoundException;
import br.edu.ifsp.foodflow.app.infra.exceptions.OrderNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@Tag("UnitTest")
@ExtendWith(MockitoExtension.class)
class AdvanceOrderItemStatusUseCaseTest {
    private  Order order;
    private Table table;
    private User user;
    private UUID orderId;
    private UUID itemId;

    @InjectMocks
    private AdvanceOrderItemStatusUseCase statusUseCaseTest;

    @Mock
    private OrderRepository orderRepository;

    @BeforeEach
    void setup(){
        orderId = UUID.randomUUID();
        itemId = UUID.randomUUID();
        table = new Table(1);
        user = new User("João Silva", "João", "joao@gmail.com", "1234");
        order = new Order(table, user);
    }

    @Test
    @DisplayName("Dado que a comanda informada é nula, quando o garçom tentar avançar o status do item, " +
            "então o sistema deve lançar um erro informando que a comanda não pode ser nula")
    void shouldThrowNullPointerExceptionWhenOrderIdIsNull(){
        assertThatNullPointerException().isThrownBy(()->statusUseCaseTest.advanceStatus(null, orderId));

    }

    @Test
    @DisplayName("Dado que o id do item informado é nulo, quando o garçom tentar avançar o status, então o sistema deve" +
            " lançar um erro informando que o id do item não pode ser nulo.")
    void shouldThrowNullPointerExceptionWhenOrItemIdIsNull(){
        assertThatNullPointerException().isThrownBy(()->statusUseCaseTest.advanceStatus(orderId,null));

    }

    @Test
    @DisplayName("Dado que a comanda não existe, quando o garçom tentar avançar o status do item, " +
            "então o sistema deve lançar um erro informando que a comanda não foi encontrada")
    void shouldThrowOrderNotFoundExceptionWhenOrderDoesNotExist() {
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());
        assertThatExceptionOfType(OrderNotFoundException.class)
                .isThrownBy(() -> statusUseCaseTest.advanceStatus(orderId, itemId));
    }

    @Test
    @DisplayName("Dado que o item informado não está na comanda, quando o garçom tentar avançar o status, " +
            "então o sistema deve lançar um erro informando que o item não foi encontrado na comanda")
    void shouldThrowOrderItemNotFoundExceptionWhenItemIsNotInOrder() {
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        assertThatExceptionOfType(OrderItemNotFoundException.class)
                .isThrownBy(() -> statusUseCaseTest.advanceStatus(orderId, itemId));
    }




}