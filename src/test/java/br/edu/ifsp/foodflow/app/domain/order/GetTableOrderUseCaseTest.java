package br.edu.ifsp.foodflow.app.domain.order;

import br.edu.ifsp.foodflow.app.domain.order.useCases.GetTableOrderUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class GetTableOrderUseCaseTest {
    @InjectMocks
    private GetTableOrderUseCase service = new GetTableOrderUseCase();

    @Test
    @DisplayName("Deve lançar IllegalArgumentException quando o ID do pedido for nulo")
    void shouldThrowExceptionWhenOrderIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> service.getOrderById(null));

        assertEquals("O ID do pedido é obrigatório.", exception.getMessage());
    }

}