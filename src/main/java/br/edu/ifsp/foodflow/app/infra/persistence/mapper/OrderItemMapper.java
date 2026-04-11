package br.edu.ifsp.foodflow.app.infra.persistence.mapper;

import br.edu.ifsp.foodflow.app.domain.orderItem.OrderItem;
import br.edu.ifsp.foodflow.app.infra.persistence.entity.OrderItemJpaEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderItemMapper {
    private final MenuItemMapper menuItemMapper;
    private final UserMapper userMapper;
    private final AddOnMapper addOnMapper;

    public OrderItemMapper(MenuItemMapper menuItemMapper, UserMapper userMapper, AddOnMapper addOnMapper) {
        this.menuItemMapper = menuItemMapper;
        this.userMapper = userMapper;
        this.addOnMapper = addOnMapper;
    }

    public OrderItemJpaEntity toJpaEntity(OrderItem domain) {
        OrderItemJpaEntity entity = new OrderItemJpaEntity();
        entity.setId(domain.getId());
        entity.setMenuItem(menuItemMapper.toJpaEntity(domain.getMenuItem()));
        entity.setWaiter(userMapper.toJpaEntity(domain.getWaiter()));
        entity.setObservations(domain.getObservations());
        entity.setStatus(domain.getStatus());
        entity.setPrice(domain.getPrice());
        entity.setAdditions(domain.getAdditions().stream()
                .map(addOnMapper::toJpaEntity).collect(Collectors.toList()));
        return entity;
    }

    public OrderItem toDomain(OrderItemJpaEntity entity) {
        return new OrderItem(
                entity.getId(),
                menuItemMapper.toDomain(entity.getMenuItem()),
                entity.getAdditions().stream().map(addOnMapper::toDomain).collect(Collectors.toList()),
                userMapper.toDomain(entity.getWaiter()),
                entity.getObservations()
        );
    }
}
