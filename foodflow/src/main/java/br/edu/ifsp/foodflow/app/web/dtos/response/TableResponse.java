package br.edu.ifsp.foodflow.app.web.dtos.response;

import br.edu.ifsp.foodflow.app.domain.table.TableStatus;

public record TableResponse(
    Integer tableNumber,
    TableStatus status
) {}
