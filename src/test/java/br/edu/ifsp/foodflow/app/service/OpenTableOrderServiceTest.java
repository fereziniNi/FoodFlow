package br.edu.ifsp.foodflow.app.service;

import br.edu.ifsp.foodflow.app.domain.order.OrderEntity;
import br.edu.ifsp.foodflow.app.domain.order.OrderRepository;
import br.edu.ifsp.foodflow.app.domain.table.TableEntity;
import br.edu.ifsp.foodflow.app.domain.table.TableRepository;
import br.edu.ifsp.foodflow.app.domain.user.UserEntity;
import br.edu.ifsp.foodflow.app.domain.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
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

    @Mock
    private UserRepository userRepository;

    private final UUID aleatoryId = UUID.randomUUID();

    private UserEntity createExistingUser() {
        return new UserEntity(
                "Teste Nome",
                "usuarioTeste",
                "teste@email.com",
                "senha123"
        );
    }
    @Test
    @DisplayName("Deve lançar IllegalArgumentException quando o ID da mesa for nulo")
    void shouldThrowExceptionWhenTableIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> service.openOrder(null, aleatoryId));

        assertEquals("O ID da mesa é obrigatório.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException quando a mesa não existir")
    void shouldThrowExceptionWhenTableDoesNotExist() {
        when(tableRepository.findById(999)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> service.openOrder(999, aleatoryId));

        assertEquals("Mesa não encontrada.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve criar um pedido quando a mesa existir")
    void shouldCreateOrderWhenTableExists() {
        TableEntity table = new TableEntity(1);

        when(tableRepository.findById(1)).thenReturn(Optional.of(table));
        when(orderRepository.save(any(OrderEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        OrderEntity order = service.openOrder(1, aleatoryId);

        assertEquals(1, order.getTable().getTableNumber(), "O número da mesa deve ser 1");
    }

    @Test
    @DisplayName("Deve lançar IllegalStateException se já existir uma comanda ativa para a mesa")
    void shouldThrowExceptionIfActiveOrderExists() {
        TableEntity table = new TableEntity(1);
        OrderEntity activeOrder = new OrderEntity(table, createExistingUser());

        when(tableRepository.findById(1)).thenReturn(Optional.of(table));
        when(orderRepository.findActiveOrderByTable(table)).thenReturn(Optional.of(activeOrder));

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> service.openOrder(1, aleatoryId));

        assertEquals("Já existe uma comanda ativa para esta mesa.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar IllegalStateException se o id do user for nulo")
    void shouldThrowExceptionIfUserIdIsNull() {
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> service.openOrder(1, null));

        assertEquals("O ID do usuário é obrigatório.", exception.getMessage());
    }


    @Test
    @DisplayName("Deve lançar exceção quando o usuário não estiver cadastrado")
    void shouldThrowExceptionWhenUserIsNotRegistered() {

        TableEntity table = new TableEntity(1);

        when(tableRepository.findById(1)).thenReturn(Optional.of(table));
        when(userRepository.findById(aleatoryId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> service.openOrder(1, aleatoryId));

        assertEquals("Usuário não encontrado", exception.getMessage());
    }

    @Test
    @DisplayName("Deve criar um pedido quando a mesa e o usuário existirem")
    void shouldCreateOrderWhenTableAndUserExist() {
        TableEntity table = new TableEntity(1);
        UserEntity user = createExistingUser(); // sua função que cria o user

        when(tableRepository.findById(1)).thenReturn(Optional.of(table));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(orderRepository.findActiveOrderByTable(table)).thenReturn(Optional.empty());
        when(orderRepository.save(any(OrderEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        OrderEntity order = service.openOrder(1, user.getId());

        assertNotNull(order, "A ordem não deve ser nula");
        assertEquals(table.getTableNumber(), order.getTable().getTableNumber(), "Mesa incorreta");
        assertEquals(user.getId(), order.getUser().getId(), "Usuário incorreto");
    }
}