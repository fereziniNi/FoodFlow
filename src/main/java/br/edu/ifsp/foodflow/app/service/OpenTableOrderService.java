package br.edu.ifsp.foodflow.app.service;

import br.edu.ifsp.foodflow.app.domain.order.OrderEntity;
import br.edu.ifsp.foodflow.app.domain.table.TableEntity;
import br.edu.ifsp.foodflow.app.domain.table.TableRepository;

public class OpenTableOrderService {
    private final TableRepository tableRepository;

    public OpenTableOrderService(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    public OrderEntity openOrder(Integer tableId){
        if (tableId == null) {
            throw new IllegalArgumentException("O ID da mesa é obrigatório.");
        }
        TableEntity table = tableRepository.findById(tableId)
                .orElseThrow(() -> new IllegalArgumentException("Mesa não encontrada."));
        return null;
    }
}
