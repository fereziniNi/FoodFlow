package br.edu.ifsp.foodflow.app.application.useCases.order;

import br.edu.ifsp.foodflow.app.domain.order.OrderRepository;
import br.edu.ifsp.foodflow.app.domain.order.dto.OrderDetailsDTO;
import br.edu.ifsp.foodflow.app.domain.orderItem.dto.AddOnSummaryDTO;
import br.edu.ifsp.foodflow.app.domain.orderItem.dto.OrderItemDetailsDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListActiveOrdersUseCase {
    private final OrderRepository orderRepository;

    public ListActiveOrdersUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderDetailsDTO> execute() {
        return orderRepository.findAllActive().stream()
                .map(order -> new OrderDetailsDTO(
                        order.getId(),
                        order.getTable().getTableNumber(),
                        order.getUser().getUsername(),
                        order.getCreatedAt(),
                        order.getActive(),
                        order.getTotalPriceOfOrder(),
                        order.getDiscountPercentage(),
                        order.getOrderItems().stream().map(
                                item -> new OrderItemDetailsDTO(
                                        item.getId(),
                                        item.getMenuItem().getName(),
                                        item.getObservations(),
                                        item.getPrice(),
                                        item.getStatus(),
                                        item.getWaiter() != null ? item.getWaiter().getUsername() : "Sistema",
                                        item.getAdditions().stream()
                                                .map(a -> new AddOnSummaryDTO(a.getName(), a.getPrice()))
                                                .toList()
                                )
                        ).toList()
                )).toList();
    }
}
