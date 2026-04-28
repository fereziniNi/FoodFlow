package br.edu.ifsp.foodflow.app.infra.persistence.mapper;

import br.edu.ifsp.foodflow.app.domain.addOn.AddOn;
import br.edu.ifsp.foodflow.app.infra.persistence.entity.AddOnJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class AddOnMapper {
    public AddOnJpaEntity toJpaEntity(AddOn addOn){
        return new AddOnJpaEntity(addOn.getId(), addOn.getName(), addOn.getPrice());
    }

    public AddOn toDomain(AddOnJpaEntity addOnJpaEntity){
        return new AddOn(addOnJpaEntity.getId(), addOnJpaEntity.getName(), addOnJpaEntity.getPrice());
    }
}
