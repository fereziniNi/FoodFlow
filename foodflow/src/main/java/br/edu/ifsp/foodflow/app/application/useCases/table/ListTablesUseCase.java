package br.edu.ifsp.foodflow.app.application.useCases.table;

import br.edu.ifsp.foodflow.app.domain.table.Table;
import br.edu.ifsp.foodflow.app.domain.table.TableRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListTablesUseCase {
    private final TableRepository tableRepository;

    public ListTablesUseCase(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    public List<Table> execute() {
        return tableRepository.findAll();
    }
}
