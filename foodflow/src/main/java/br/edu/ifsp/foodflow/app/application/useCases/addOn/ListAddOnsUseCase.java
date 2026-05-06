package br.edu.ifsp.foodflow.app.application.useCases.addOn;

import br.edu.ifsp.foodflow.app.domain.addOn.AddOn;
import br.edu.ifsp.foodflow.app.domain.addOn.AddOnRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListAddOnsUseCase {
    private final AddOnRepository addOnRepository;

    public ListAddOnsUseCase(AddOnRepository addOnRepository) {
        this.addOnRepository = addOnRepository;
    }

    public List<AddOn> execute() {
        return addOnRepository.findAll();
    }
}
