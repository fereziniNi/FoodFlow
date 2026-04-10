package br.edu.ifsp.foodflow.app.infra.persistence.repository.impl;

import br.edu.ifsp.foodflow.app.domain.addOn.AddOn;
import br.edu.ifsp.foodflow.app.domain.addOn.AddOnRepository;
import br.edu.ifsp.foodflow.app.infra.persistence.mapper.AddOnMapper;
import br.edu.ifsp.foodflow.app.infra.persistence.repository.springdata.SpringDataAddOnRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class AddOnRepositoryImpl implements AddOnRepository {

    private final SpringDataAddOnRepository springDataRepository;
    private final AddOnMapper mapper;

    @Override
    public AddOn save(AddOn addOn) {
        var entity = mapper.toJpaEntity(addOn);
        var savedEntity = springDataRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }


    @Override
    public Optional<AddOn> findById(UUID id) {
        return springDataRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<AddOn> findAllById(List<UUID> ids) {
        return springDataRepository.findAllById(ids).stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<AddOn> findAll() {
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