package br.edu.ifsp.foodflow.app.web.dtos.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RemoveItemFromOrderRequest(
        @NotNull(message = "O ID do item a ser removido é obrigatório")
        UUID orderItemId
) {}