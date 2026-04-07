package br.edu.ifsp.foodflow.app.domain.order;


import br.edu.ifsp.foodflow.app.domain.order.useCases.CloseOrderUseCase;
import br.edu.ifsp.foodflow.app.domain.table.TableEntity;
import br.edu.ifsp.foodflow.app.domain.user.UserEntity;
import jakarta.persistence.Table;
import org.junit.jupiter.api.BeforeEach;
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

    private UserEntity user;
    private  TableEntity table;

    @BeforeEach
    void setup(){
        table = new TableEntity(1);
        user = new UserEntity("João Silva","João","joao@gmail.com","1234");
    }


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
    void shouldThrowsNoSuchElementExceptionWhenOrderNotExists(){
        UUID randomUUID = UUID.randomUUID();
        when(orderRepository.findById(randomUUID)).thenReturn(Optional.empty());
        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(()->closeOrderUseCase.closeOrder(randomUUID,2));
    }

    @Test
    @DisplayName("Dado que a comanda já está fechada, quando o cliente tentar fechá-la novamente, então um erro deve " +
            "ser lançado informando que a comanda já está fechada.")
    void shouldThrowsIllegalStateExceptionWhenOrderNotExists(){
        UUID randomUUID = UUID.randomUUID();
        OrderEntity order = new OrderEntity(table,user);
        order.markAsClosed();
        when(orderRepository.findById(randomUUID)).thenReturn(Optional.of(order));
        assertThatIllegalStateException().isThrownBy(()->closeOrderUseCase.closeOrder(randomUUID,2));
    }

    @Test
    @DisplayName("\n" +
            "Como usuário, eu quero fechar a comanda e calcular o total com desconto podendo dividi-lo igualmente " +
            "entre os clientes, para que cada um saiba o valor correto a pagar.")
    void shouldThrowsIllegalArgumentExceptionWhenNumberOfPeopleIsLowerThan1(){
        UUID randomUUID = UUID.randomUUID();
        OrderEntity order = new OrderEntity(table,user);
        when(orderRepository.findById(randomUUID)).thenReturn(Optional.of(order));
        assertThatIllegalArgumentException()
                .isThrownBy(()->closeOrderUseCase.closeOrder(randomUUID,-1));
    }

}
