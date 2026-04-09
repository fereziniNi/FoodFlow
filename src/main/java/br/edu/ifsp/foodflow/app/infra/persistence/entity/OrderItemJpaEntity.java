package br.edu.ifsp.foodflow.app.infra.persistence.entity;

import br.edu.ifsp.foodflow.app.domain.orderItem.OrderItemStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderJpaEntity order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_item_id")
    private MenuItemJpaEntity menuItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "waiter_id")
    private UserJpaEntity waiter;

    @ManyToMany
    @JoinTable(
            name = "order_item_addons",
            joinColumns = @JoinColumn(name = "order_item_id"),
            inverseJoinColumns = @JoinColumn(name = "add_on_id")
    )
    private List<AddOnJpaEntity> additions = new ArrayList<>();

    private String observations;

    @Enumerated(EnumType.STRING)
    private OrderItemStatus status;

    private double price;
}