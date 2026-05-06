package br.edu.ifsp.foodflow.app.web.controllers;

import br.edu.ifsp.foodflow.app.application.useCases.user.RegisterUserUseCase;
import br.edu.ifsp.foodflow.app.domain.user.UserRepository;
import br.edu.ifsp.foodflow.app.infra.security.TokenService;
import br.edu.ifsp.foodflow.app.web.dtos.request.LoginRequest;
import br.edu.ifsp.foodflow.app.web.dtos.request.RegisterRequest;
import br.edu.ifsp.foodflow.app.web.dtos.response.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Registro e login de usuários")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final RegisterUserUseCase registerUserUseCase;
    private final TokenService tokenService;
    private final UserRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager, RegisterUserUseCase registerUserUseCase, TokenService tokenService, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.registerUserUseCase = registerUserUseCase;
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Operation(summary = "Realizar login e obter token JWT")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login realizado com sucesso, token retornado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição"),
        @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var user = userRepository.findByUsername(data.username())
                .orElseThrow(() -> new RuntimeException("User not found after authentication"));

        var token = tokenService.generateToken((UserDetails) Objects.requireNonNull(auth.getPrincipal()));

        return ResponseEntity.ok(new LoginResponse(token, user.getId(), user.getUsername()));
    }

    @Operation(summary = "Cadastrar novo usuário (garçom ou admin)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuário cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição")
    })
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequest data) {
        this.registerUserUseCase.execute(data.name(), data.username(), data.email(), data.password(), data.role());
        return ResponseEntity.ok().build();
    }
}
