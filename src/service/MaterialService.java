package service;

import domain.entities.Material;
import repository.MaterialRepository;
import repository.interfaces.MaterialInterface;

import java.util.List;
import java.util.Optional;

public class MaterialService implements MaterialInterface<Material> {

    private final MaterialRepository materialRepository;

    public MaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    @Override
    public Material save(Material material) {
        return materialRepository.save(material);
    }

    @Override
    public Optional<Material> findById(Material material) {
        return materialRepository.findById(material);
    }

    @Override
    public List<Material> findAll() {
        return materialRepository.findAll();
    }

    @Override
    public Material update(Material material) {
        return materialRepository.update(material);
    }

    @Override
    public boolean delete(Material material) {
        return materialRepository.delete(material);
    }
}
