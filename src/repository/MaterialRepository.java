package repository;

import config.Database;
import domain.entities.Component;
import domain.entities.Material;
import domain.entities.Project;
import exceptions.MaterialsNotFoundException;
import repository.interfaces.MaterialInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        Component savedComponent = componentRepository.save(material);
        material.setId(savedComponent.getId());

        String sql = "INSERT INTO materials (id, name, unitCost, quantity, transportCost, qualityCoefficient, project_id, componentType, vatRate) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, material.getId());
            preparedStatement.setString(2, material.getName());
            preparedStatement.setDouble(3, material.getUnitCost());
            preparedStatement.setDouble(4, material.getQuantity());
            preparedStatement.setDouble(5, material.getTransportCost());
            preparedStatement.setDouble(6, material.getCoefficientQuality());
            preparedStatement.setInt(7, material.getProject().getId());
            preparedStatement.setString(8, material.getComponentType());
            preparedStatement.setDouble(9, material.getVatRate());

            System.out.println(preparedStatement);
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
    public Material update(Material material) {
        Component updatedComponent = componentRepository.update(material);
        material.setId(updatedComponent.getId());

        String sql = "UPDATE materials SET name = ? , vatrate = ? ,unitCost = ?, quantity = ?, transportCost = ?, qualityCoefficient = ? " +
                "WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, material.getName());
            preparedStatement.setDouble(2, material.getVatRate());
            preparedStatement.setDouble(3, material.getUnitCost());
            preparedStatement.setDouble(4, material.getQuantity());
            preparedStatement.setDouble(5, material.getTransportCost());
            preparedStatement.setDouble(6, material.getCoefficientQuality());
            preparedStatement.setInt(7, material.getId());
            System.out.println(preparedStatement);
            int rowsAffected = preparedStatement.executeUpdate();
            if(rowsAffected == 0) {
                throw new MaterialsNotFoundException("something wrong when updating material");
            }
        } catch (SQLException e) {
            System.out.println("Error updating material: " + e.getMessage());
        }

        return material;
    }


    @Override
    public Optional<Material> findById(int id) {
        String sql = "SELECT " +
                "    m.id AS materialId, " +
                "    m.unitCost, " +
                "    m.quantity, " +
                "    m.transportCost, " +
                "    m.qualityCoefficient, " +
                "    m.name AS componentName, " +
                "    m.vatRate, " +
                "    m.project_id " +
                "FROM materials m " +
                "WHERE m.id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Material foundMaterial = new Material();
                foundMaterial.setId(resultSet.getInt("materialId"));
                foundMaterial.setUnitCost(resultSet.getDouble("unitCost"));
                foundMaterial.setQuantity(resultSet.getDouble("quantity"));
                foundMaterial.setTransportCost(resultSet.getDouble("transportCost"));
                foundMaterial.setCoefficientQuality(resultSet.getDouble("qualityCoefficient"));
                foundMaterial.setName(resultSet.getString("componentName"));
                foundMaterial.setVatRate(resultSet.getDouble("vatRate"));

                Project project = new Project();
                project.setId(resultSet.getInt("project_id"));
                foundMaterial.setProject(project);

                return Optional.of(foundMaterial);
            }
        } catch (SQLException sqlException) {
            System.out.println("Error finding material: " + sqlException.getMessage());
        }

        return Optional.empty();
    }


    @Override
    public List<Material> findAll() {
        String sql = "SELECT " +
                "    m.id AS materialId, " +
                "    m.unitCost, " +
                "    m.quantity, " +
                "    m.transportCost, " +
                "    m.qualityCoefficient, " +
                "    m.name AS componentName, " +
                "    m.vatRate, " +
                "    m.project_id " +
                "FROM materials m";

        List<Material> materials = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Material material = new Material();
                material.setId(resultSet.getInt("materialId"));
                material.setUnitCost(resultSet.getDouble("unitCost"));
                material.setQuantity(resultSet.getDouble("quantity"));
                material.setTransportCost(resultSet.getDouble("transportCost"));
                material.setCoefficientQuality(resultSet.getDouble("qualityCoefficient"));
                material.setName(resultSet.getString("componentName"));
                material.setVatRate(resultSet.getDouble("vatRate"));

                Project project = new Project();
                project.setId(resultSet.getInt("project_id"));
                material.setProject(project);

                materials.add(material);
            }
        } catch (SQLException e) {
            System.out.println("Error finding materials: " + e.getMessage());
        }

        return materials;
    }




    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM materials WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                System.out.println("Material deleted successfully with ID: " + id);
            } else {
                throw new MaterialsNotFoundException("material delete issue");
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return false;
    }

    @Override
    public List<Material> findAllByProjectId(int id) {
        List<Material> materials = new ArrayList<>();
        String sql = "SELECT m.id, m.unitCost, m.quantity, m.transportCost, m.qualityCoefficient, " +
                "m.name AS component_name, m.vatRate, m.project_id " +
                "FROM materials m " +
                "WHERE m.project_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Project project = new Project();
                project.setId(resultSet.getInt("project_id"));

                Material material = new Material();
                material.setId(resultSet.getInt("id"));
                material.setUnitCost(resultSet.getDouble("unitCost"));
                material.setQuantity(resultSet.getDouble("quantity"));
                material.setTransportCost(resultSet.getDouble("transportCost"));
                material.setCoefficientQuality(resultSet.getDouble("qualityCoefficient"));
                material.setName(resultSet.getString("component_name"));
                material.setVatRate(resultSet.getDouble("vatRate"));

                material.setProject(project);

                materials.add(material);
            }
        } catch (SQLException e) {
            System.out.println("Error finding materials by project ID: " + e.getMessage());
        }

        return materials;
    }
}
