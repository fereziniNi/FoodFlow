package br.edu.ifsp.foodflow.app.application.useCases.order;

import br.edu.ifsp.foodflow.app.domain.addOn.AddOn;
import br.edu.ifsp.foodflow.app.domain.addOn.AddOnRepository;
import br.edu.ifsp.foodflow.app.domain.menuItem.MenuItem;
import br.edu.ifsp.foodflow.app.domain.menuItem.MenuItemRepository;
import br.edu.ifsp.foodflow.app.domain.order.Order;
import br.edu.ifsp.foodflow.app.domain.order.OrderRepository;
import br.edu.ifsp.foodflow.app.domain.order.dto.AddItemToOrderRequest;
import br.edu.ifsp.foodflow.app.domain.order.dto.OrderResponse;
import br.edu.ifsp.foodflow.app.domain.orderItem.OrderItem;
import br.edu.ifsp.foodflow.app.domain.user.User;
import br.edu.ifsp.foodflow.app.domain.user.UserRepository;
import br.edu.ifsp.foodflow.app.infra.exceptions.OrderAlreadyClosedException;
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

    public OrderResponse execute(AddItemToOrderRequest item){
        UUID orderId = item.orderId();
        Objects.requireNonNull(orderId, "O ID do pedido não pode ser nulo");
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("Pedido não encontrado para o ID: " + orderId));

        if(!order.getActive()) throw new OrderAlreadyClosedException("Pedido já finalizado para o ID: " + orderId);

        OrderItem orderItem = validateOrderItem(item);

        order.addOrderItem(orderItem);
        orderRepository.save(order);
        return new OrderResponse(order.getId(), order.getTable().getTableNumber(), order.getCreatedAt(), order.getActive(), order.getTotalPriceOfOrder());
    }

    private OrderItem validateOrderItem(AddItemToOrderRequest item){
        Objects.requireNonNull(item, "O request do item não pode ser nulo");

        MenuItem menuItem = menuItemRepository.findById(item.menuItemId())
                .orElseThrow(() -> new NoSuchElementException("Item do menu não encontrado para o ID: " + item.menuItemId()));

        if (menuItem.getAvailableQuantity() <= 0) throw new UnavailableItemException("O item solicitado encontra-se indisponível.");

        User waiter = userRepository.findById(item.waiterId())
                .orElseThrow(() -> new UserNotFoundException("O garçom informado não foi encontrado."));

        List<AddOn> addOns = new ArrayList<>();
        if (item.addOnIds() != null && !item.addOnIds().isEmpty()) {
            addOns = addOnRepository.findAllById(item.addOnIds());

            if (addOns.size() != item.addOnIds().size())
                throw new NoSuchElementException("Um ou mais adicionais informados são inválidos ou foram removidos.");
        }

        return new OrderItem(UUID.randomUUID(), menuItem, addOns, waiter, item.observations());
    }
}
