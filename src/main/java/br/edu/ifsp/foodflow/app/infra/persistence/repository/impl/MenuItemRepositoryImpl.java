package br.edu.ifsp.foodflow.app.infra.persistence.repository.impl;

import br.edu.ifsp.foodflow.app.domain.menuItem.MenuItem;
import br.edu.ifsp.foodflow.app.domain.menuItem.MenuItemRepository;
import br.edu.ifsp.foodflow.app.infra.persistence.mapper.MenuItemMapper;
import br.edu.ifsp.foodflow.app.infra.persistence.repository.springdata.SpringDataMenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MenuItemRepositoryImpl implements MenuItemRepository {

    private final SpringDataMenuItemRepository springDataRepository;
    private final MenuItemMapper mapper;

    @Override
    public MenuItem save(MenuItem menuItem) {
        var entity = mapper.toJpaEntity(menuItem);
        var savedEntity = springDataRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<MenuItem> findById(UUID id) {
        return springDataRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<MenuItem> findAll() {
        return springDataRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        springDataRepository.deleteById(id);
    }
}