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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Tag("UnitTest")
@ExtendWith(MockitoExtension.class)
class RegisterUserUseCaseTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

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

        @Test
        @DisplayName("Deve salvar usuário com senha criptografada quando username não existe")
        void deveSalvarUsuarioComSenhaCriptografada() {
            when(userRepository.findByUsername("bia")).thenReturn(Optional.empty());
            when(passwordEncoder.encode("123")).thenReturn("$2a$hash");

            registerUserUseCase.execute("Bia", "bia", "bia@email.com", "123", UserRole.WAITER);

            verify(passwordEncoder).encode("123");
            verify(userRepository).save(any(User.class));
        }
    }

}