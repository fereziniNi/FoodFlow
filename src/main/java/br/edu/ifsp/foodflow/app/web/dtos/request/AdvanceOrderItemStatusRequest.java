package br.edu.ifsp.foodflow.app.web.dtos.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AdvanceOrderItemStatusRequest(
        @NotNull
        UUID itemId
) {
}
