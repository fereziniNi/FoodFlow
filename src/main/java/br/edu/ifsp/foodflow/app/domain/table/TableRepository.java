package br.edu.ifsp.foodflow.app.domain.table;

import jakarta.persistence.Table;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableRepository extends JpaRepository<TableEntity, Integer> {
}
