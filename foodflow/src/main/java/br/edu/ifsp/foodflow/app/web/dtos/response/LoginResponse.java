package br.edu.ifsp.foodflow.app.web.dtos.response;

import java.util.UUID;

public record LoginResponse(
        String token,
        UUID userId,
        String username
) {
}
