package br.edu.ifsp.foodflow.app.web.dtos.request;

import br.edu.ifsp.foodflow.app.domain.user.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterRequest(
        @NotBlank String name,
        @NotBlank String username,
        @Email @NotBlank String email,
        @NotBlank String password,
        @NotNull UserRole role
) {
}
