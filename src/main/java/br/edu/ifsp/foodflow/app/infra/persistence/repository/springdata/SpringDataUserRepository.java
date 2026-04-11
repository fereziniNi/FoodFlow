package br.edu.ifsp.foodflow.app.infra.persistence.repository.springdata;

import br.edu.ifsp.foodflow.app.infra.persistence.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataUserRepository extends JpaRepository<UserJpaEntity, UUID> {
    Optional<UserJpaEntity> findByUsername(String username);
}