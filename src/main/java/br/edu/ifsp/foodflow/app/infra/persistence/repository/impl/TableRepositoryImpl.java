package br.edu.ifsp.foodflow.app.infra.persistence.repository.impl;

import br.edu.ifsp.foodflow.app.domain.table.Table;
import br.edu.ifsp.foodflow.app.domain.table.TableRepository;
import br.edu.ifsp.foodflow.app.infra.persistence.mapper.TableMapper;
import br.edu.ifsp.foodflow.app.infra.persistence.repository.springdata.SpringDataTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class TableRepositoryImpl implements TableRepository {

    private final SpringDataTableRepository springDataRepository;
    private final TableMapper mapper;

    @Override
    public Table save(Table table) {
        var entity = mapper.toJpaEntity(table);
        var savedEntity = springDataRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Table> findByTableNumber(Integer tableNumber) {
        return springDataRepository.findById(tableNumber)
                .map(mapper::toDomain);
    }

    @Override
    public List<Table> findAll() {
        return springDataRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}