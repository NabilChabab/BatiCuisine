package service;

import domain.entities.Component;
import repository.ComponentRepository;
import service.interfaces.ComponentService;

import java.util.List;
import java.util.Optional;

public class ComponentServiceImpl implements ComponentService {

    private final ComponentRepository componentRepository;
    public ComponentServiceImpl(ComponentRepository componentRepository) {
        this.componentRepository = componentRepository;
    }

    @Override
    public Component save(Component component) {
        return this.componentRepository.save(component);
    }


    @Override
    public Optional<Component> findById(int component) {
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
