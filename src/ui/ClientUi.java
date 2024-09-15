package ui;

import domain.entities.Client;
import service.ClientService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class ClientUi {

    private ClientService clientService;
    private Scanner scanner;

    public ClientUi(ClientService clientService) {
        this.clientService = clientService;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        while (true) {
            System.out.println("=== Client Management ===");
            System.out.println("1. Add new client");
            System.out.println("2. Find client by ID");
            System.out.println("3. View all clients");
            System.out.println("4. Update client");
            System.out.println("5. Delete client");
            System.out.println("6. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    addClient();
                    break;
                case 2:
                    findClientById();
                    break;
                case 3:
                    viewAllClients();
                    break;
                case 4:
                    updateClient();
                    break;
                case 5:
                    deleteClient();
                    break;
                case 6:
                    System.out.println("Exiting Client Management...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addClient() {
        System.out.println("Enter client's name:");
        String name = scanner.nextLine();

        System.out.println("Enter client's address:");
        String address = scanner.nextLine();

        System.out.println("Enter client's phone number:");
        String phone = scanner.nextLine();

        System.out.println("Is the client a professional? (true/false):");
        boolean isProfessional = scanner.nextBoolean();

        Client client = new Client();
        client.setName(name);
        client.setaddress(address);
        client.setphone(phone);
        client.setProfessional(isProfessional);
        clientService.save(client);
        System.out.println("Client added successfully!");
    }

    private void findClientById() {
        System.out.println("Enter client ID:");
        int clientId = scanner.nextInt();

        // Create a Client object with the ID to pass to findById
        Client clientWithId = new Client();
        clientWithId.setId(clientId);

        // Find the client by the ID
        Optional<Client> clientOptional = clientService.findById(clientWithId);

        // Debugging information
        System.out.println("Searched for Client ID: " + clientId);

        // Check if the client is present
        if (clientOptional.isPresent()) {
            // Display the client info using the client retrieved from the database
            displayClientInfo(clientOptional.get());
        } else {
            System.out.println("Client not found.");
        }
    }

    private void viewAllClients() {
        List<Client> clients = clientService.findAll();
        if (!clients.isEmpty()) {
            for (Client client : clients) {
                displayClientInfo(client);
            }
        } else {
            System.out.println("No clients available.");
        }
    }

    private void updateClient() {
        System.out.println("Enter the client ID to update:");
        int clientId = scanner.nextInt();

        Client clientToFind = new Client();
        clientToFind.setId(clientId);
        Optional<Client> clientOptional = clientService.findById(clientToFind);

        if (clientOptional.isPresent()) {
            Client clientToUpdate = clientOptional.get();

            System.out.println("Enter new client's name :");
            String name = scanner.nextLine();
            if (!name.isEmpty()) {
                clientToUpdate.setName(name);
            }

            System.out.println("Enter new client's address :");
            String address = scanner.nextLine();
            if (!address.isEmpty()) {
                clientToUpdate.setaddress(address);
            }

            System.out.println("Enter new client's phone number :");
            String phone = scanner.nextLine();
            if (!phone.isEmpty()) {
                clientToUpdate.setphone(phone);
            }

            System.out.println("Is the client a professional? (y/n):");
            String isProfessionalInput = scanner.nextLine().toLowerCase();
            if (!isProfessionalInput.isEmpty()) {
                boolean isProfessional = isProfessionalInput.equals("y");
                clientToUpdate.setProfessional(isProfessional);
            }

            clientService.update(clientToUpdate);
            System.out.println("Client updated successfully!");
        } else {
            System.out.println("Client not found.");
        }
    }

    private void deleteClient() {
        System.out.println("Enter the client ID to delete:");
        int clientId = scanner.nextInt();


        Client client = new Client();
        client.setId(clientId);
        Optional<Client> clientOptional =   clientService.findById(client);
        if (clientOptional != null) {
            clientService.delete(client);
            System.out.println("Client deleted successfully!");
        } else {
            System.out.println("Client not found.");
        }
    }

    private void displayClientInfo(Client client) {
        System.out.println("Client ID: " + client.getId());
        System.out.println("Name: " + client.getName());
        System.out.println("Address: " + client.getaddress());
        System.out.println("Phone: " + client.getphone());
        System.out.println("Professional: " + (client.isProfessional() ? "Yes" : "No"));
        System.out.println("========================");
    }
}
