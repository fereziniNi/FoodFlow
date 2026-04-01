package br.edu.ifsp.foodflow.app.domain.orderItem;

import br.edu.ifsp.foodflow.app.domain.addOn.AddOnEntity;
import br.edu.ifsp.foodflow.app.domain.menuItem.MenuItemEntity;
import br.edu.ifsp.foodflow.app.domain.user.UserEntity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private MenuItemEntity menuItem;
    private List<AddOnEntity> additions;
    private UserEntity waiter;
    private String observations;
    private OrderItemStatusENUM status;

    public OrderItemEntity(UUID id, MenuItemEntity menuItem, List<AddOnEntity> additions, UserEntity waiter, String observations) {
        this.id = id;
        this.menuItem = menuItem;
        this.additions = additions;
        this.waiter = waiter;
        this.observations = observations;
        this.status = OrderItemStatusENUM.PENDING;
    }
}
