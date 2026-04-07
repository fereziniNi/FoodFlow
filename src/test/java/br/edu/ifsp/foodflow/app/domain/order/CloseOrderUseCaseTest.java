package br.edu.ifsp.foodflow.app.domain.order;


import br.edu.ifsp.foodflow.app.domain.order.dto.CloseOrderResponse;
import br.edu.ifsp.foodflow.app.domain.order.dto.OrderResponse;
import br.edu.ifsp.foodflow.app.domain.order.useCases.CloseOrderUseCase;
import br.edu.ifsp.foodflow.app.domain.table.TableEntity;
import br.edu.ifsp.foodflow.app.domain.user.UserEntity;
import jakarta.persistence.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.bind.annotation.PathVariable;

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
    private TableEntity table;
    private OrderEntity order;
    private UUID randomUUID;

    @BeforeEach
    void setup(){
        randomUUID = UUID.randomUUID();
        table = new TableEntity(1);
        user = new UserEntity("João Silva","João","joao@gmail.com","1234");
        order = new OrderEntity(table,user);
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
        when(orderRepository.findById(randomUUID)).thenReturn(Optional.empty());
        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(()->closeOrderUseCase.closeOrder(randomUUID,2));
    }

    @Test
    @DisplayName("Dado que a comanda já está fechada, quando o cliente tentar fechá-la novamente, então um erro deve " +
            "ser lançado informando que a comanda já está fechada.")
    void shouldThrowsIllegalStateExceptionWhenOrderNotExists(){
        order.markAsClosed();
        when(orderRepository.findById(randomUUID)).thenReturn(Optional.of(order));
        assertThatIllegalStateException().isThrownBy(()->closeOrderUseCase.closeOrder(randomUUID,2));
    }

    @ParameterizedTest(name = "[{index}]: número de pessoas igual a {0} should throws IllegalArgumentException")
    @ValueSource(ints = {-1,0})
    @DisplayName("Dado que a comanda está aberta e o número de pessoas informado para divisão da conta é zero" +
            " ou negativo, quando o cliente tentar fechar a comanda, deve ser lançado um erro de argumento inválido")
    void shouldThrowsIllegalArgumentExceptionWhenNumberOfPeopleIsLowerThan1(int numberOfPeople){
        when(orderRepository.findById(randomUUID)).thenReturn(Optional.of(order));
        assertThatIllegalArgumentException()
                .isThrownBy(()->closeOrderUseCase.closeOrder(randomUUID,numberOfPeople));
    }
    @Test                                                                                                                                                                               @DisplayName("Dado que a comanda está aberta e o total é menor que R$ 100,00, quando o cliente fechar a comanda, " +                                                                        "então deve retornar o resumo sem desconto")
    void shouldReturnCloseOrderResponseWithoutDiscount() {
        when(orderRepository.findById(randomUUID)).thenReturn(Optional.of(order));
        CloseOrderResponse closeOrderResponse = closeOrderUseCase.closeOrder(randomUUID,1);
        assertThat(closeOrderResponse.totalWithoutDiscount()).isEqualTo(0);
        assertThat(closeOrderResponse.discountPercentage()).isEqualTo(0);
        assertThat(closeOrderResponse.totalWithDiscount()).isEqualTo(0);
        assertThat(closeOrderResponse.totalPerPerson()).isEqualTo(0);

    }

}
