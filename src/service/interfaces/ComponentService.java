package service.interfaces;

import domain.entities.Component;
import repository.interfaces.ComponentInterface;

import java.util.List;
import java.util.Optional;

public interface ComponentService extends ComponentInterface<Component> {
    Component save(Component component);

    @Override
    Optional<Component> findById(int component);

    @Override
    List<Component> findAll();

    @Override
    Component update(Component component);

    @Override
    boolean delete(Component component);
}
