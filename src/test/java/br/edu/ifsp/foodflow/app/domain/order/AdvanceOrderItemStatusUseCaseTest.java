package br.edu.ifsp.foodflow.app.domain.order;


import br.edu.ifsp.foodflow.app.application.useCases.order.AdvanceOrderItemStatusUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@Tag("UnitTest")
class AdvanceOrderItemStatusUseCaseTest {

    @InjectMocks
    private AdvanceOrderItemStatusUseCase statusUseCaseTest;
    @Test
    @DisplayName("Dado que a comanda informada é nula, quando o garçom tentar avançar o status do item, " +
            "então o sistema deve lançar um erro informando que a comanda não pode ser nula")
    void shouldThrowNullPointerExceptionWhenOrderIdIsNull(){
        assertThatNullPointerException().isThrownBy(()->statusUseCaseTest.advanceStatus(null, UUID.randomUUID()));

    }

    @Test
    @DisplayName("Dado que o id do item informado é nulo, quando o garçom tentar avançar o status, então o sistema deve" +
            " lançar um erro informando que o id do item não pode ser nulo.")
    void shouldThrowNullPointerExceptionWhenOrItemIdIsNull(){
        assertThatNullPointerException().isThrownBy(()->statusUseCaseTest.advanceStatus(UUID.randomUUID(),null));

    }




}