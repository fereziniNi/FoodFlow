package br.edu.ifsp.foodflow.app.web.controllers;

import br.edu.ifsp.foodflow.app.application.useCases.table.ListTablesUseCase;
import br.edu.ifsp.foodflow.app.web.dtos.response.TableResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tables")
@Tag(name = "Mesas", description = "Consulta de mesas do restaurante")
@SecurityRequirement(name = "bearer-jwt")
public class TableController {
    private final ListTablesUseCase listTablesUseCase;

    public TableController(ListTablesUseCase listTablesUseCase) {
        this.listTablesUseCase = listTablesUseCase;
    }

    @Operation(summary = "Listar todas as mesas")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de mesas retornada com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<TableResponse>> listAll() {
        List<TableResponse> response = listTablesUseCase.execute()
                .stream()
                .map(table -> new TableResponse(table.getTableNumber(), table.getStatus()))
                .toList();
        return ResponseEntity.ok(response);
    }
}
