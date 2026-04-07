package br.edu.ifsp.foodflow.app.domain.order;


import br.edu.ifsp.foodflow.app.domain.order.useCases.CloseOrderUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CloseOrderUseCaseTest {

    @InjectMocks
    private CloseOrderUseCase closeOrderUseCase;

    @Mock
    private OrderRepository orderRepository;


    @Test
    @DisplayName("Dado que a comanda informada é nula, quando o cliente tentar fechá-la, então deve ser lançado " +
            "um erro de comanda nula")
    void shouldThrowsNullPointerExceptionWhenOrderIdIsNull(){
        assertThatNullPointerException()
                .isThrownBy(()->closeOrderUseCase.closeOrder(null,2))
                .withMessage("O ID do pedido não pode ser nulo");
    }

    @Test
    @DisplayName("Dado que a comanda não existe, quando o cliente tentar fechá-la, então deve ser lançado um erro" +
            " de comanda inexistente")
    void shouldThrowsIllegalArgumentExceptionWhenOrderNotExists(){
        UUID randomUUID = UUID.randomUUID();
        when(orderRepository.existsById(randomUUID)).thenReturn(false);
        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(()->closeOrderUseCase.closeOrder(randomUUID,2));
    }

}
