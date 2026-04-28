package br.edu.ifsp.foodflow.app.infra.persistence.entity;

import br.edu.ifsp.foodflow.app.domain.table.TableStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "restaurant_tables")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TableJpaEntity {
    @Id
    private Integer tableNumber;

    @Enumerated(EnumType.STRING)
    private TableStatus status;
}