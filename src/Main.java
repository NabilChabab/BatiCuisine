import config.Database;
import domain.entities.Client;
import repository.ClientRepository;
import service.ClientService;
import ui.ClientUi;

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

            // Create repository and service instances
            ClientRepository clientRepository = new ClientRepository(connection);
            ClientService clientService = new ClientService(clientRepository);

            // Create and launch the UI
            ClientUi clientUi = new ClientUi(clientService);
            clientUi.showMenu();

        } catch (SQLException e) {
            System.out.println("Error: Unable to establish a database connection.");
            e.printStackTrace();
        }
    }
}