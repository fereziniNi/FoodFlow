package br.edu.ifsp.foodflow.app.infra.persistence.repository.impl;

import br.edu.ifsp.foodflow.app.domain.order.Order;
import br.edu.ifsp.foodflow.app.domain.order.OrderRepository;
import br.edu.ifsp.foodflow.app.domain.table.Table;
import br.edu.ifsp.foodflow.app.infra.persistence.mapper.OrderMapper;
import br.edu.ifsp.foodflow.app.infra.persistence.mapper.TableMapper;
import br.edu.ifsp.foodflow.app.infra.persistence.repository.springdata.SpringDataOrderRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final SpringDataOrderRepository springDataOrderRepository;
    private final OrderMapper orderMapper;
    private final TableMapper tableMapper;

    @Override
    public Optional<Order> findActiveOrderByTable(Table table) {
        var tableEntity = tableMapper.toJpaEntity(table);
        return springDataOrderRepository.findByTableAndActiveTrue(tableEntity)
                .map(orderMapper::toDomain);
    }

    @Override
    public Order save(Order order) {
        var entity = orderMapper.toJpaEntity(order);
        var savedEntity = springDataOrderRepository.save(entity);
        return orderMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return springDataOrderRepository.findById(id)
                .map(orderMapper::toDomain);
    }
}
