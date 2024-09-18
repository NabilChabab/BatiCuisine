package service.interfaces;

import domain.entities.Material;
import repository.interfaces.MaterialInterface;

import java.util.List;
import java.util.Optional;

public interface MaterialService extends MaterialInterface<Material> {
    @Override
    Material save(Material material);

    @Override
    Optional<Material> findById(Material material);

    @Override
    List<Material> findAll();

    @Override
    Material update(Material material);

    @Override
    boolean delete(Material material);
}
