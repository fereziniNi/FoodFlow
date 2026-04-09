package br.edu.ifsp.foodflow.app.domain.addOn;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AddOnRepository {
    AddOn save(AddOn addOn);
    Optional<AddOn> findById(UUID id);
    List<AddOn> findAll();
    void deleteById(UUID id);
}