package br.edu.ifsp.foodflow.app.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OpenTableOrderServiceTest {
    private OpenTableOrderService service;

    @Test
    @DisplayName("Deve lançar IllegalArgumentException quando o ID da mesa for nulo")
    void shouldThrowExceptionWhenTableIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> service.openOrder(null));

        assertEquals("O ID da mesa é obrigatório.", exception.getMessage());
    }

}