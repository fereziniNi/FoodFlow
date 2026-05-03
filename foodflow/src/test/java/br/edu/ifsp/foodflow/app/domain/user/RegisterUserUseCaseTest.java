package br.edu.ifsp.foodflow.app.domain.user;

import br.edu.ifsp.foodflow.app.application.useCases.user.RegisterUserUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@Tag("UnitTest")
@ExtendWith(MockitoExtension.class)
class RegisterUserUseCaseTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RegisterUserUseCase registerUserUseCase;


    @Tag("Structural")
    @Nested
    class StructuralTests{
        @Test
        @DisplayName("Deve lançar exceção quando username já existe")
        void deveLancarExcecaoQuandoUsernameJaExiste() {
            User existing = new User(
                    UUID.randomUUID(),
                    "Bia alves",
                    "bia",
                    "bia@email.com",
                    "senha",
                    UserRole.WAITER
            );
            when(userRepository.findByUsername("bia")).thenReturn(Optional.of(existing));

            assertThatThrownBy(() ->
                    registerUserUseCase.execute(
                            "Bia Pereira",
                            "bia",
                            "bya@email.com",
                            "123",
                            UserRole.WAITER)
            ).isInstanceOf(RuntimeException.class);
        }

    }

}