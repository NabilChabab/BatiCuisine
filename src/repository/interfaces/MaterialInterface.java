package repository.interfaces;

import domain.entities.Material;

import java.util.List;
import java.util.Optional;

public interface MaterialInterface  <T extends Material> extends CrudInterface<Material>{
    @Override
    public Material save(Material entity);

    @Override
    public Optional<Material> findById(int material);

    @Override
    public List<Material> findAll();

    @Override
    public Material update(Material entity);

    @Override
    public boolean delete(Material entity);
}
