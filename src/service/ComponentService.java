package service;

import domain.entities.Component;
import repository.ComponentRepository;
import repository.interfaces.ComponentInterface;

import java.util.List;
import java.util.Optional;

public class ComponentService  implements ComponentInterface<Component> {

    private final ComponentRepository componentRepository;
    public ComponentService(ComponentRepository componentRepository) {
        this.componentRepository = componentRepository;
    }

    public Component save(Component component) {
        return this.componentRepository.save(component);
    }


    @Override
    public Optional<Component> findById(Component component) {
        return Optional.empty();
    }

    @Override
    public List<Component> findAll() {
        return List.of();
    }

    @Override
    public Component update(Component component) {
        return null;
    }

    @Override
    public boolean delete(Component component) {
        return false;
    }
}
