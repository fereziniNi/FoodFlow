package br.edu.ifsp.foodflow.app.domain.order;

import br.edu.ifsp.foodflow.app.domain.orderItem.OrderItem;
import br.edu.ifsp.foodflow.app.domain.orderItem.OrderItemStatus;
import br.edu.ifsp.foodflow.app.domain.table.Table;
import br.edu.ifsp.foodflow.app.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class Order {
    private UUID id;
    private Table table;
    private List<OrderItem> orderItems;
    private LocalDateTime createdAt;
    private Boolean active;
    private User user;

    public Order(Table table, User user) {
        if (table == null)throw new IllegalArgumentException("Um pedido não pode ser aberto sem uma mesa.");
        if (user == null) throw new IllegalArgumentException("Um pedido deve ter um usuário responsável.");

        this.table = table;
        this.user = user;
        this.orderItems = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
        this.active = true;
    }

    public void addOrderItem(OrderItem item) {
        this.orderItems.add(item);
    }

    public void removeOrderItem(OrderItem item){
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
        return orderItems.stream().mapToDouble(OrderItem::getPrice).sum();
    }

    public double getDiscountPercentage() {
        double total = getTotalPriceOfOrder();
        if (total >= 250) return 0.20;
        if (total >= 200) return 0.10;
        if (total >= 100) return 0.05;
        return 0.0;
    }

}
