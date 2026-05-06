package br.edu.ifsp.foodflow.app.web.controllers;

import br.edu.ifsp.foodflow.app.application.useCases.table.ListTablesUseCase;
import br.edu.ifsp.foodflow.app.web.dtos.response.TableResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tables")
public class TableController {
    private final ListTablesUseCase listTablesUseCase;

    public TableController(ListTablesUseCase listTablesUseCase) {
        this.listTablesUseCase = listTablesUseCase;
    }

    @GetMapping
    public ResponseEntity<List<TableResponse>> listAll() {
        List<TableResponse> response = listTablesUseCase.execute()
                .stream()
                .map(table -> new TableResponse(table.getTableNumber(), table.getStatus()))
                .toList();
        return ResponseEntity.ok(response);
    }
}
