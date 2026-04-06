package br.edu.ifsp.foodflow.app.domain.addOn;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddOnRepository extends JpaRepository<AddOnEntity, UUID> {
}
