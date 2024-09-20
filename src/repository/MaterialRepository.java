package repository;

import config.Database;
import domain.entities.Material;
import repository.interfaces.MaterialInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MaterialRepository implements MaterialInterface<Material> {
    private Connection connection;
    private final ComponentRepository componentRepository;

    public MaterialRepository(ComponentRepository componentRepository) throws SQLException {
        this.connection = Database.getInstance().getConnection();
        this.componentRepository = componentRepository;

    }


    @Override
    public Material save(Material material) {
        String sql = "INSERT INTO materials (component_id, unitCost, quantity, transportCost, qualityCoefficient) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, material.getComponent().getId());
            preparedStatement.setDouble(2, material.getUnitCost());
            preparedStatement.setDouble(3, material.getQuantity());
            preparedStatement.setDouble(4, material.getTransportCost());
            preparedStatement.setDouble(5, material.getCoefficientQuality());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int generatedId = resultSet.getInt(1);
                material.setId(generatedId);
                System.out.println("Material saved successfully with ID: " + generatedId);
            } else {
                throw new SQLException("Failed to save material, no ID obtained.");
            }
        } catch (SQLException e) {
            System.out.println("Error saving material: " + e.getMessage());
        }

        return material;
    }



    @Override
    public Optional<Material> findById(int material) {
        return Optional.empty();
    }

    @Override
    public List<Material> findAll() {
        return List.of();
    }

    @Override
    public Material update(Material entity) {
        return null;
    }

    @Override
    public boolean delete(Material entity) {
        return false;
    }
}
