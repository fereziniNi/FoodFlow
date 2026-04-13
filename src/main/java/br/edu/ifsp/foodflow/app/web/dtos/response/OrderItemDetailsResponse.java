package br.edu.ifsp.foodflow.app.web.dtos.response;

import java.util.UUID;

public record OrderItemDetailsResponse(
        UUID id,
        String description,
        double price
) {}