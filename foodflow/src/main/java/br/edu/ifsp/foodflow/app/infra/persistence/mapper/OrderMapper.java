package br.edu.ifsp.foodflow.app.infra.persistence.mapper;

import br.edu.ifsp.foodflow.app.domain.order.Order;
import br.edu.ifsp.foodflow.app.infra.persistence.entity.OrderJpaEntity;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class OrderMapper {
    private final OrderItemMapper itemMapper;
    private final TableMapper tableMapper;
    private final UserMapper userMapper;

    public OrderMapper(OrderItemMapper itemMapper, TableMapper tableMapper, UserMapper userMapper) {
        this.itemMapper = itemMapper;
        this.tableMapper = tableMapper;
        this.userMapper = userMapper;
    }

    public OrderJpaEntity toJpaEntity(Order domain) {
        OrderJpaEntity entity = new OrderJpaEntity();
        entity.setId(domain.getId());
        entity.setTable(tableMapper.toJpaEntity(domain.getTable()));
        entity.setUser(userMapper.toJpaEntity(domain.getUser()));
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setActive(domain.getActive());

        if (domain.getOrderItems() != null) {
            entity.setOrderItems(domain.getOrderItems().stream()
                    .map(item -> {
                        var itemEntity = itemMapper.toJpaEntity(item);
                        itemEntity.setOrder(entity);
                        return itemEntity;
                    }).collect(Collectors.toList()));
        }
        return entity;
    }

    public Order toDomain(OrderJpaEntity entity) {
        return new Order(
                entity.getId(),
                tableMapper.toDomain(entity.getTable()),
                entity.getOrderItems().stream().map(itemMapper::toDomain).collect(Collectors.toList()),
                entity.getCreatedAt(),
                entity.getActive(),
                userMapper.toDomain(entity.getUser())
        );
    }
}