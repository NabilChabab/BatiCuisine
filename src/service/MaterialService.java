package service;

import domain.entities.Material;
import repository.ComponentRepository;
import repository.MaterialRepository;

import java.util.List;
import java.util.Optional;

public class MaterialService{


    private final MaterialRepository materialRepository;
    private ComponentRepository componentRepository;

    public MaterialService(MaterialRepository materialRepository, ComponentRepository componentRepository) {
        this.materialRepository = materialRepository;
        this.componentRepository = componentRepository;
    }

    public Material save(Material material) {
        return materialRepository.save(material);
    }


    public Optional<Material> findById(int id) {
        return materialRepository.findById(id);
    }


    public List<Material> findAll() {
        return materialRepository.findAll();
    }


    public Material update(Material material) {
        return materialRepository.update(material);
    }


    public boolean delete(int id) {
        return materialRepository.delete(id);
    }

    public List<Material> findAllByProjectId(int projectId) {
        return materialRepository.findAllByProjectId(projectId);
    }
    private double getVatRateForMaterial(Material material) {
        return componentRepository.findTvaForComponent(material.getId());
    }


    public double calculateMaterial(Material material) {
        double total = material.getUnitCost() * material.getQuantity() * material.getCoefficientQuality();
        return total + material.getTransportCost();
    }

    public double calculateMaterialAfterVatRate(Material material) {
        return calculateMaterial(material);
    }

    private double applyVat(double cost, double vatRate) {
        return cost + (cost * vatRate / 100);
    }

    public double calculateMaterialBeforeVatRate(Material material) {
        double costBeforeVat = calculateMaterial(material);
        double vatRate = getVatRateForMaterial(material);
        return applyVat(costBeforeVat, vatRate);
    }
}
