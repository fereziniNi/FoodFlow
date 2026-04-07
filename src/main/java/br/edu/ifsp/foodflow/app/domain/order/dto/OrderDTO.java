package br.edu.ifsp.foodflow.app.domain.order.dto;

import br.edu.ifsp.foodflow.app.domain.orderItem.dto.OrderItemDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class OrderDTO {
    private UUID orderId;
    private int tableNumber;
    private String userName;
    private LocalDateTime createdAt;
    private boolean active;
    private double total;
    private double discount;
    private List<OrderItemDTO> items;
}