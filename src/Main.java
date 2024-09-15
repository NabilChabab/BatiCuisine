import config.Database;
import domain.entities.Client;
import domain.entities.Project;
import domain.enums.ProjectStatus;
import repository.ClientRepository;
import repository.ProjectRepository;
import service.ClientService;
import service.ProjectService;
import ui.ClientUi;
import ui.ProjectUi;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws SQLException {
        try {
            // Initialize the database connection
            Connection connection = Database.getInstance().getConnection();

            ProjectRepository projectRepository = new ProjectRepository(connection);
            ProjectService projectService = new ProjectService(projectRepository);

            ProjectUi projectUi = new ProjectUi(projectService);
            projectUi.displayMenu();

        } catch (SQLException e) {
            System.out.println("Error: Unable to establish a database connection.");
            e.printStackTrace();
        }
    }
}