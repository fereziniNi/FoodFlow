package br.edu.ifsp.foodflow.app.domain.user;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(UUID id);
    Optional<User> findByUsername(String username);
    List<User> findAll();
    void deleteById(UUID id);
}