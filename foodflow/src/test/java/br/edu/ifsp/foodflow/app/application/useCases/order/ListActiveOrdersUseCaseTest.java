package br.edu.ifsp.foodflow.app.application.useCases.order;

import br.edu.ifsp.foodflow.app.domain.addOn.AddOn;
import br.edu.ifsp.foodflow.app.domain.menuItem.MenuItem;
import br.edu.ifsp.foodflow.app.domain.order.Order;
import br.edu.ifsp.foodflow.app.domain.order.OrderRepository;
import br.edu.ifsp.foodflow.app.domain.order.dto.OrderDetailsDTO;
import br.edu.ifsp.foodflow.app.domain.orderItem.OrderItem;
import br.edu.ifsp.foodflow.app.domain.orderItem.dto.AddOnSummaryDTO;
import br.edu.ifsp.foodflow.app.domain.table.Table;
import br.edu.ifsp.foodflow.app.domain.user.User;
import br.edu.ifsp.foodflow.app.domain.user.UserRole;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@Tag("UnitTest")
@ExtendWith(MockitoExtension.class)
public class ListActiveOrdersUseCaseTest {
    private User waiter;
    private Table table;
    private Order order;
    private MenuItem menuItem;

    @BeforeEach
    void setUp(){
        waiter = new User("Joao", "joao", "joao@email.com", "123", UserRole.WAITER);
        table = new Table(2);
        order = new Order(table,waiter);
        menuItem = new MenuItem(UUID.randomUUID(), "X-Bacon", "Ingredientes", 35.0, 15);
    }

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private ListActiveOrdersUseCase sut;

    @Tag("Structural")
    @Nested
    @DisplayName("Testes Estruturais (com instâncias reais)")
    class StructuralTests {
        @Test
        @DisplayName("Deve mapear corretamente um pedido quando o item não tiver um garçom responsável, atribuindo a string 'Sistema'")
        void shouldMapOrderAndItemsWhenItemHasNoWaiter() {

            OrderItem item = new OrderItem(UUID.randomUUID(), menuItem, List.of(), null, "Com gelo");
            order.addOrderItem(item);

            when(orderRepository.findAllActive()).thenReturn(List.of(order));

            List<OrderDetailsDTO> result = sut.execute();

            assertThat(result).isNotNull();
            assertThat(result.size()).isEqualTo(1);

            OrderDetailsDTO orderDetails = result.getFirst();
            assertThat(orderDetails.items().size()).isEqualTo(1);
            assertThat(orderDetails.items().getFirst().waiterName()).isEqualTo("Sistema");

            verify(orderRepository, times(1)).findAllActive();
        }
    }

    @Test
    @DisplayName("Deve mapear corretamente um pedido e seus itens quando o item possuir um garçom responsável")
    void shouldMapOrderAndItemsWhenItemHasWaiter() {
        OrderItem item = new OrderItem(UUID.randomUUID(), menuItem, List.of(), waiter, "Sem cebola");
        order.addOrderItem(item);

        when(orderRepository.findAllActive()).thenReturn(List.of(order));

        List<OrderDetailsDTO> result = sut.execute();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);

        OrderDetailsDTO orderDetails = result.getFirst();
        assertThat(orderDetails.tableNumber()).isEqualTo(table.getTableNumber());
        assertThat(orderDetails.userName()).isEqualTo("joao");
        assertThat(orderDetails.total()).isEqualTo(35.0);

        assertThat(orderDetails.items().size()).isEqualTo(1);
        assertThat(orderDetails.items().getFirst().waiterName()).isEqualTo("joao");

        verify(orderRepository, times(1)).findAllActive();
    }


    @Nested
    @Tag("Mutation")
    @DisplayName("Testes de Mutação")
    class MutationTests {
        @Test
        @DisplayName("Deve mapear corretamente um pedido que possui itens com adicional")
        void shouldMapOrderAndItemsWhenItemHasAdditional() {

            List<AddOn> addOns = List.of(
                    new AddOn("Bacon extra", 5.0),
                    new AddOn("Queijo Extra", 3.0)
            );

            OrderItem item = new OrderItem(UUID.randomUUID(), menuItem, addOns, waiter, "Sem cebola");
            order.addOrderItem(item);

            when(orderRepository.findAllActive()).thenReturn(List.of(order));

            List<OrderDetailsDTO> result = sut.execute();
            OrderDetailsDTO orderDetails = result.getFirst();

            assertThat(orderDetails.items().getFirst().additions()).isEqualTo(
                    addOns.stream().map(addOn -> new AddOnSummaryDTO(addOn.getName(), addOn.getPrice())).toList());

        }
    }
}