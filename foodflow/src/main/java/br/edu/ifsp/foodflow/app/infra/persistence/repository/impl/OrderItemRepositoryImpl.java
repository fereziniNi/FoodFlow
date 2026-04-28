package br.edu.ifsp.foodflow.app.infra.persistence.repository.impl;

import br.edu.ifsp.foodflow.app.domain.orderItem.OrderItem;
import br.edu.ifsp.foodflow.app.domain.orderItem.OrderItemRepository;
import br.edu.ifsp.foodflow.app.domain.orderItem.OrderItemStatus;
import br.edu.ifsp.foodflow.app.infra.persistence.mapper.OrderItemMapper;
import br.edu.ifsp.foodflow.app.infra.persistence.repository.springdata.SpringDataOrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderItemRepositoryImpl implements OrderItemRepository {
    private final SpringDataOrderItemRepository springDataRepository;
    private final OrderItemMapper mapper;

    @Override
    public OrderItem save(OrderItem orderItem) {
        var entity = mapper.toJpaEntity(orderItem);
        var savedEntity = springDataRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<OrderItem> findById(UUID id) {
        return springDataRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<OrderItem> findByStatus(OrderItemStatus status) {
        return springDataRepository.findByStatus(status)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
