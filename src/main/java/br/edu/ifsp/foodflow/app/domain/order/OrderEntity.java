package br.edu.ifsp.foodflow.app.domain.order;

import br.edu.ifsp.foodflow.app.domain.orderItem.OrderItemEntity;
import br.edu.ifsp.foodflow.app.domain.orderItem.OrderItemStatus;
import br.edu.ifsp.foodflow.app.domain.table.TableEntity;
import br.edu.ifsp.foodflow.app.domain.user.UserEntity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private TableEntity table;
    private List<OrderItemEntity> orderItems;
    private LocalDateTime createdAt;
    private Boolean active;
    private UserEntity user;

    public OrderEntity(TableEntity table, UserEntity user) {
        if (table == null)throw new IllegalArgumentException("Um pedido não pode ser aberto sem uma mesa.");
        if (user == null) throw new IllegalArgumentException("Um pedido deve ter um usuário responsável.");

        this.table = table;
        this.user = user;
        this.orderItems = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
        this.active = true;
    }

    public void addOrderItem(OrderItemEntity item) {
        this.orderItems.add(item);
    }

    public void removeOrderItem(OrderItemEntity item){
        if (!this.active)
            throw new IllegalStateException("Não é possível alterar uma comanda já encerrada.");

        if (item.getStatus() != OrderItemStatus.PENDING)
            throw new IllegalStateException("Não é possível remover um item que já está em preparo ou finalizado.");
        this.orderItems.remove(item);
    }

    public void markAsClosed(){
        this.active = false;
    }

    public double getTotalPriceOfOrder(){
        return orderItems.stream().mapToDouble(OrderItemEntity::getPrice).sum();
    }

    public double getDiscountPercentage() {
        double total = getTotalPriceOfOrder();
        if (total >= 250) return 0.20;
        if (total >= 200) return 0.10;
        if (total >= 100) return 0.05;
        return 0.0;
    }


}
