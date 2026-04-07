package br.edu.ifsp.foodflow.app.domain.order.useCases;

import br.edu.ifsp.foodflow.app.domain.addOn.AddOnEntity;
import br.edu.ifsp.foodflow.app.domain.addOn.AddOnRepository;
import br.edu.ifsp.foodflow.app.domain.menuItem.MenuItemEntity;
import br.edu.ifsp.foodflow.app.domain.menuItem.MenuItemRepository;
import br.edu.ifsp.foodflow.app.domain.order.OrderEntity;
import br.edu.ifsp.foodflow.app.domain.order.OrderRepository;
import br.edu.ifsp.foodflow.app.domain.order.dto.AddItemToOrderRequest;
import br.edu.ifsp.foodflow.app.domain.order.dto.OrderResponse;
import br.edu.ifsp.foodflow.app.domain.orderItem.OrderItemEntity;
import br.edu.ifsp.foodflow.app.domain.user.UserEntity;
import br.edu.ifsp.foodflow.app.domain.user.UserRepository;
import br.edu.ifsp.foodflow.app.infra.exceptions.UnavailableItemException;
import br.edu.ifsp.foodflow.app.infra.exceptions.UserNotFoundException;

import java.util.*;

public class AddItemToOrderUseCase {
    private final OrderRepository orderRepository;
    private final MenuItemRepository menuItemRepository;
    private final UserRepository userRepository;
    private final AddOnRepository addOnRepository;

    public AddItemToOrderUseCase(OrderRepository orderRepository, MenuItemRepository menuItemRepository, UserRepository userRepository, AddOnRepository addOnRepository) {
        this.orderRepository = orderRepository;
        this.menuItemRepository = menuItemRepository;
        this.userRepository = userRepository;
        this.addOnRepository = addOnRepository;
    }

    public OrderResponse execute(UUID orderUUID, AddItemToOrderRequest item){
        Objects.requireNonNull(orderUUID, "O ID do pedido não pode ser nulo");
        OrderEntity order = orderRepository.findById(orderUUID)
                .orElseThrow(() -> new NoSuchElementException("Pedido não encontrado para o ID: " + orderUUID));

        OrderItemEntity orderItem = validateOrderItem(item);

        order.addOrderItem(orderItem);
        orderRepository.save(order);
        return new OrderResponse(order.getId(), order.getTable().getTableNumber(), order.getCreatedAt(), order.getActive(), order.getTotalPriceOfOrder());
    }

    private OrderItemEntity validateOrderItem(AddItemToOrderRequest item){
        Objects.requireNonNull(item, "O request do item não pode ser nulo");

        MenuItemEntity menuItem = menuItemRepository.findById(item.menuItemId())
                .orElseThrow(() -> new NoSuchElementException("Item do menu não encontrado para o ID: " + item.menuItemId()));

        if (menuItem.getAvailableQuantity() <= 0) throw new UnavailableItemException("O item solicitado encontra-se indisponível.");

        UserEntity waiter = userRepository.findById(item.waiterId())
                .orElseThrow(() -> new UserNotFoundException("O garçom informado não foi encontrado."));

        List<AddOnEntity> addOns = new ArrayList<>();
        if (item.addOnIds() != null && !item.addOnIds().isEmpty()) {
            addOns = addOnRepository.findAllById(item.addOnIds());

            if (addOns.size() != item.addOnIds().size())
                throw new NoSuchElementException("Um ou mais adicionais informados são inválidos ou foram removidos.");
        }

        return new OrderItemEntity(UUID.randomUUID(), menuItem, addOns, waiter, item.observations());
    }
}
