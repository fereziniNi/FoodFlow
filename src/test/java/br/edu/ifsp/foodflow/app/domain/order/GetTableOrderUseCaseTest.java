package br.edu.ifsp.foodflow.app.domain.order;

import br.edu.ifsp.foodflow.app.domain.order.useCases.GetTableOrderUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

        assertEquals("O ID do pedido é obrigatório.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException quando a pedido não existir")
    void shouldThrowExceptionWhenOrderDoesNotExist() {
        when(orderRepository.findById(notExistId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> service.getOrderById(notExistId));

        assertEquals("Pedido não encontrada.", exception.getMessage());
    }

}