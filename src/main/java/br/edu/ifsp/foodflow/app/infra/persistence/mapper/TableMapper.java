package br.edu.ifsp.foodflow.app.infra.persistence.mapper;

import br.edu.ifsp.foodflow.app.domain.table.Table;

import br.edu.ifsp.foodflow.app.infra.persistence.entity.TableJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class TableMapper {
    public TableJpaEntity toJpaEntity(Table table){
        return new TableJpaEntity(table.getTableNumber(), table.getStatus());
    }
    public Table toDomain(TableJpaEntity table){
        return new Table(table.getTableNumber(), table.getStatus());
    }
}
