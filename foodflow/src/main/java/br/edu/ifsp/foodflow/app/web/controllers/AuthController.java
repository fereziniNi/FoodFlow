package br.edu.ifsp.foodflow.app.web.controllers;

import br.edu.ifsp.foodflow.app.application.useCases.user.RegisterUserUseCase;
import br.edu.ifsp.foodflow.app.infra.security.TokenService;
import br.edu.ifsp.foodflow.app.web.dtos.request.LoginRequest;
import br.edu.ifsp.foodflow.app.web.dtos.request.RegisterRequest;
import br.edu.ifsp.foodflow.app.web.dtos.response.LoginResponse;
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
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final RegisterUserUseCase registerUserUseCase;
    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, RegisterUserUseCase registerUserUseCase, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.registerUserUseCase = registerUserUseCase;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((UserDetails) Objects.requireNonNull(auth.getPrincipal()));

        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequest data) {
        this.registerUserUseCase.execute(data.name(), data.username(), data.email(), data.password(), data.role());
        return ResponseEntity.ok().build();
    }
}
