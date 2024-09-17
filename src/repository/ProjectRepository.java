package repository;



import config.Database;
import domain.entities.*;
import exceptions.ProjectsNotFoundException;
import repository.interfaces.ProjectInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjectRepository implements ProjectInterface {
    private final Connection connection;
    private final ClientRepository clientRepository;
    private final ComponentRepository componentRepository;
    private final MaterialRepository materialRepository;
    private final WorkForceRepository workForceRepository;

    public ProjectRepository(ClientRepository clientRepository, ComponentRepository componentRepository, MaterialRepository materialRepository, WorkForceRepository workForceRepository) throws SQLException {
        this.clientRepository = clientRepository;
        this.componentRepository = componentRepository;
        this.materialRepository = materialRepository;
        this.workForceRepository = workForceRepository;
        this.connection = Database.getInstance().getConnection();
    }

    @Override
    public Project save(Project project) {
        String sql = "INSERT INTO projects (projectName, profitMargin, totalCost, status, surface, client_id) VALUES (?, ?, ?, ?::projectStatus, ?, ?) RETURNING id;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, project.getProjectName());
            preparedStatement.setDouble(2, project.getProfitMargin());
            preparedStatement.setDouble(3, project.getTotalCost());
            preparedStatement.setString(4, project.getStatus().name());
            preparedStatement.setDouble(5, project.getSurface());
            preparedStatement.setInt(6, project.getClient().getId());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                project.setId(resultSet.getInt("id"));
                System.out.println("Project saved with ID: " + project.getId());
                return project;
            } else {
                throw new SQLException("Creating project failed, no ID obtained.");
            }
        } catch (SQLException e) {
            System.out.println("Error saving project: " + e.getMessage());
            return null;
        }
    }



    @Override
    public void saveClientProject(Client client, Project project, Material material, WorkForce workForce) {
        try {
            connection.setAutoCommit(false);

            Client savedClient = clientRepository.save(client);
            project.setClient(savedClient);


            Component materialComponent = new Component();
            materialComponent.setName(material.getName());
            materialComponent.setComponentType("Material");
            materialComponent.setVatRate(material.getVatRate());
            materialComponent.setProject(project);

            Component savedMaterialComponent = componentRepository.save(materialComponent);

            material.setComponent(savedMaterialComponent);
            materialRepository.save(material);

            Component workforceComponent = new Component();
            workforceComponent.setName(workForce.getName());
            workforceComponent.setComponentType("Workforce");
            workforceComponent.setVatRate(workForce.getVatRate());
            workforceComponent.setProject(project);

            Component savedWorkforceComponent = componentRepository.save(workforceComponent);


            workForce.setComponent(savedWorkforceComponent);
            workForceRepository.save(workForce);

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                System.out.println("Error during transaction rollback: " + rollbackEx.getMessage());
            }
            System.out.println("Error saving client and project: " + e.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Error resetting auto-commit: " + e.getMessage());
            }
        }
    }





    @Override
    public Optional<Project> findById(Project project) {
        String sql = "SELECT * FROM projects WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, project.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Client client = new Client();
                client.setId(resultSet.getInt("client_id"));
                Project foundProject = new Project(
                        resultSet.getInt("id"),
                        resultSet.getString("projectName"),
                        resultSet.getDouble("profitMargin"),
                        resultSet.getDouble("totalCost"),
                        resultSet.getString("status"),
                        resultSet.getDouble("surface"),
                        client
                );
                return Optional.of(foundProject);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Project> findAll() {
        String sql = "SELECT * FROM projects";
        List<Project> projects = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Client client = new Client();
                client.setId(resultSet.getInt("client_id"));
                Project project = new Project(
                        resultSet.getInt("id"),
                        resultSet.getString("projectName"),
                        resultSet.getDouble("profitMargin"),
                        resultSet.getDouble("totalCost"),
                        resultSet.getString("status"),
                        resultSet.getDouble("surface"),
                        client
                );
                projects.add(project);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return projects;
    }

    @Override
    public Project update(Project project) {
        String sql = "UPDATE projects SET projectName = ?, profitMargin = ?, totalCost = ?, status = ?::projectStatus , surface = ?, client_id = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, project.getProjectName());
            preparedStatement.setDouble(2, project.getProfitMargin());
            preparedStatement.setDouble(3, project.getTotalCost());
            preparedStatement.setString(4, project.getStatus().name());
            preparedStatement.setString(5, project.getStatus().name());
            preparedStatement.setDouble(6, project.getSurface());
            preparedStatement.setInt(7, project.getId());

            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                System.out.println("Project updated successfully");
            } else {
                throw new ProjectsNotFoundException("Update failed, project not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return project;
    }

    @Override
    public boolean delete(Project project) {
        String sql = "DELETE FROM projects WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, project.getId());

            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                System.out.println("Project deleted successfully");
                return true;
            } else {
                throw new ProjectsNotFoundException("Delete failed, project not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
