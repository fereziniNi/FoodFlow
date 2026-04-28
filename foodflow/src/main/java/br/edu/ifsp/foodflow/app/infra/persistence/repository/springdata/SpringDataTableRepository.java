package br.edu.ifsp.foodflow.app.infra.persistence.repository.springdata;

import br.edu.ifsp.foodflow.app.infra.persistence.entity.TableJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataTableRepository extends JpaRepository<TableJpaEntity, Integer> {
}