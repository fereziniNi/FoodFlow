package br.edu.ifsp.foodflow.app.service;

import br.edu.ifsp.foodflow.app.domain.order.OrderEntity;
import br.edu.ifsp.foodflow.app.domain.order.OrderRepository;
import br.edu.ifsp.foodflow.app.domain.table.TableEntity;
import br.edu.ifsp.foodflow.app.domain.table.TableRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OpenTableOrderServiceTest {
    @InjectMocks
    private OpenTableOrderService service;

    @Mock
    private TableRepository tableRepository;

    @Mock
    private OrderRepository orderRepository;

    @Test
    @DisplayName("Deve lançar IllegalArgumentException quando o ID da mesa for nulo")
    void shouldThrowExceptionWhenTableIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> service.openOrder(null));

        assertEquals("O ID da mesa é obrigatório.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException quando a mesa não existir")
    void shouldThrowExceptionWhenTableDoesNotExist() {
        when(tableRepository.findById(999)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> service.openOrder(999));

        assertEquals("Mesa não encontrada.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve criar um pedido quando a mesa existir")
    void shouldCreateOrderWhenTableExists() {
        TableEntity table = new TableEntity(1);

        when(tableRepository.findById(1)).thenReturn(Optional.of(table));
        when(orderRepository.save(any(OrderEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        OrderEntity order = service.openOrder(1);

        assertEquals(1, order.getTable().getTableNumber(), "O número da mesa deve ser 1");
    }

}