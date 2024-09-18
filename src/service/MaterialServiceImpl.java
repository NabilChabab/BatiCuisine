package service;

import domain.entities.Material;
import repository.MaterialRepository;
import service.interfaces.MaterialService;

import java.util.List;
import java.util.Optional;

public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepository;

    public MaterialServiceImpl(MaterialRepository materialRepository) {
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
