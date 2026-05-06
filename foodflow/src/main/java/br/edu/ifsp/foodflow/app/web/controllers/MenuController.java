package br.edu.ifsp.foodflow.app.web.controllers;

import br.edu.ifsp.foodflow.app.application.useCases.addOn.ListAddOnsUseCase;
import br.edu.ifsp.foodflow.app.application.useCases.menuItem.ListMenuItemsUseCase;
import br.edu.ifsp.foodflow.app.domain.addOn.AddOn;
import br.edu.ifsp.foodflow.app.domain.menuItem.MenuItem;
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
@RequestMapping("/menu")
@Tag(name = "Cardápio", description = "Consulta de itens do cardápio e adicionais")
@SecurityRequirement(name = "bearer-jwt")
public class MenuController {
    private final ListMenuItemsUseCase listMenuItemsUseCase;
    private final ListAddOnsUseCase listAddOnsUseCase;

    public MenuController(ListMenuItemsUseCase listMenuItemsUseCase, ListAddOnsUseCase listAddOnsUseCase) {
        this.listMenuItemsUseCase = listMenuItemsUseCase;
        this.listAddOnsUseCase = listAddOnsUseCase;
    }

    @Operation(summary = "Listar todos os itens do cardápio")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de itens retornada com sucesso")
    })
    @GetMapping("/items")
    public ResponseEntity<List<MenuItem>> listItems() {
        return ResponseEntity.ok(listMenuItemsUseCase.execute());
    }

    @Operation(summary = "Listar todos os adicionais disponíveis")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de adicionais retornada com sucesso")
    })
    @GetMapping("/addons")
    public ResponseEntity<List<AddOn>> listAddOns() {
        return ResponseEntity.ok(listAddOnsUseCase.execute());
    }
}
