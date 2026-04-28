package br.edu.ifsp.foodflow.app.web.dtos.request;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record AddItemToOrderRequest(
        @NotNull(message = "O ID do item do menu é obrigatório")
        UUID menuItemId,

        @NotNull(message = "O ID do garçom é obrigatório")
        UUID waiterId,

        List<UUID> addOnIds,
        String observations
) {}