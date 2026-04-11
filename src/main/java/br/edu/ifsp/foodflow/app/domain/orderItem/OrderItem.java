package br.edu.ifsp.foodflow.app.domain.orderItem;

import br.edu.ifsp.foodflow.app.domain.addOn.AddOn;
import br.edu.ifsp.foodflow.app.domain.menuItem.MenuItem;
import br.edu.ifsp.foodflow.app.domain.user.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
public class OrderItem {
    private UUID id;
    private MenuItem menuItem;
    private List<AddOn> additions;
    private User waiter;
    private String observations;
    private OrderItemStatus status;
    private double price;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public OrderItem(UUID id, MenuItem menuItem, List<AddOn> additions, User waiter, String observations) {
        this.id = id;
        this.menuItem = menuItem;
        this.additions = additions;
        this.waiter = waiter;
        this.observations = observations;
        this.status = OrderItemStatus.PENDING;
        this.price = menuItem.getPrice() + additions.stream().mapToDouble(AddOn::getPrice).sum();
        this.createAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
    }
    public OrderItem(UUID id, MenuItem menuItem, List<AddOn> additions,
                     User waiter, String observations, OrderItemStatus status,
                     LocalDateTime createAt,LocalDateTime updateAt) {
        this.id = id;
        this.menuItem = menuItem;
        this.additions = additions;
        this.waiter = waiter;
        this.observations = observations;
        this.status = status;
        this.price = menuItem.getPrice() + additions.stream().mapToDouble(AddOn::getPrice).sum();
        this.createAt = createAt;
        this.updateAt =  updateAt;
    }



    public void upgradeProgress(){
        this.status = switch (this.status) {
            case PENDING -> OrderItemStatus.PREPARATION;
            case PREPARATION -> OrderItemStatus.FINISHED;
            case FINISHED -> throw new IllegalStateException("O item já está finalizado e não pode avançar.");
        };
        this.updateAt = LocalDateTime.now();
    }
}
