package br.edu.ifsp.foodflow.app.web.dtos.request;

import jakarta.validation.constraints.Min;

public record CloseOrderRequest (
        @Min(1)
        int numberOfPeople
){}
