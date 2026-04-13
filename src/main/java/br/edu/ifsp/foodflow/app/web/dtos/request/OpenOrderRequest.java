package br.edu.ifsp.foodflow.app.web.dtos.request;


import java.util.UUID;

public record OpenOrderRequest(
        UUID userId

) {}