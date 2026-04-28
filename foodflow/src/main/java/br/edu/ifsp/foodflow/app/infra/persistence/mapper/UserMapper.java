package br.edu.ifsp.foodflow.app.infra.persistence.mapper;

import br.edu.ifsp.foodflow.app.domain.user.User;
import br.edu.ifsp.foodflow.app.infra.persistence.entity.UserJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserJpaEntity toJpaEntity(User domain) {
        return new UserJpaEntity(domain.getId(), domain.getName(), domain.getUsername(), domain.getEmail(), domain.getPassword());
    }

    public User toDomain(UserJpaEntity entity) {
        return new User(entity.getId(), entity.getName(), entity.getUsername(), entity.getEmail(), entity.getPassword());
    }
}