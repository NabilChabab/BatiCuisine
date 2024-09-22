package ui;

import domain.entities.Client;
import exceptions.ClientNotFoundException;
import service.ClientService;

import java.util.Optional;
import java.util.Scanner;

public class ClientMenu {
    private final ClientService clientService;
    private static Scanner scanner;

    public ClientMenu(ClientService clientService) {
        this.clientService = clientService;
        scanner = new Scanner(System.in);
    }


    public void displayMenu() {
        while (true) {
            System.out.println("\n" + drawTableHeader("👤 Client Management Menu"));
            System.out.println(drawTableRow("🆕 1. Add a New Client"));
            System.out.println(drawTableRow("🔍 2. Find a Client"));
            System.out.println(drawTableRow("🚪 3. Update a Client"));
            System.out.println(drawTableRow("🚪 4. Delete a Client"));
            System.out.println(drawTableRow("🚪 5. Exit"));
            System.out.println(drawTableFooter());
            System.out.print("\n👉 Enter your choice (1-3): ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addNewClient();
                    break;
                case 2:
                    findClientById();
                    break;
                case 3:
                    updateClient();
                    break;
                case 4:
                    deleteClient();
                    break;
                case 5:
                    System.out.println("👋 Exiting...");
                    return;
                default:
                    System.out.println("⚠️ Invalid choice. Please try again.");

            }

        }
    }

    public Client searchByName(String name) {
        Optional<Client> optionalClient = this.clientService.findByName(name);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            System.out.println("\n" + drawTableHeader("🎉 Client found! 🎉"));
            System.out.println(drawClientTable(client));

            while (true) {
                System.out.print("👉 Would you like to continue with this client? (y/n): ");
                String choiceToContinue = scanner.nextLine().trim().toLowerCase();
                if (choiceToContinue.equals("y")) {
                    return client;
                } else if (choiceToContinue.equals("n")) {
                    return addNewClient();
                } else {
                    System.out.println("⚠️ Invalid choice. Please enter 'y' or 'n'.");
                }
            }
        } else {
            throw new ClientNotFoundException("❌ Client not found.");
        }
    }

    public Client addNewClient() {
        System.out.println("\n" + drawTableHeader("🆕 Add a New Client ✏️"));
        System.out.print("📛 Enter the name for the client: ");
        String name = scanner.nextLine();

        // Check if client with the same name already exists
        Optional<Client> existingClient = clientService.findByName(name);
        if (existingClient.isPresent()) {
            System.out.println("\n❌ A client with the name '" + name + "' already exists.");
            return null; // Exit the method without adding the client
        }

        System.out.print("🏠 Enter the address for the client: ");
        String address = scanner.nextLine();
        System.out.print("📞 Enter the phone number for the client: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("🛠️ Is the client professional? (true/false): ");
        boolean status = scanner.nextBoolean();
        scanner.nextLine();  // Consume the newline

        Client client = new Client(0, name, address, phoneNumber, status);
        Client savedClient = clientService.save(client);

        System.out.println("\n✅ Client added successfully!\n");
        System.out.println(drawClientTable(savedClient));

        return savedClient;
    }


    public void findClientById() {
        System.out.println("\n" + drawTableHeader("🔍 Find a Client 🔍"));
        System.out.print("🆔 Enter the ID of the client: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Optional<Client> optionalClient = clientService.findById(id);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            System.out.println("\n" + drawTableHeader("🎉 Client found! 🎉"));
            System.out.println(drawClientTable(client));
        } else {
            System.out.println("❌ Client not found.");
        }
    }

    public void updateClient() {
        System.out.println("\n" + drawTableHeader("🔍 Update a Client 🔍"));
        System.out.print("🆔 Enter the ID of the client: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Optional<Client> optionalClient = clientService.findById(id);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            System.out.println("\n" + drawTableHeader("🎉 Client found! 🎉"));
            System.out.println(drawClientTable(client));

            System.out.print("📛 Enter the new name for the client: ");
            String name = scanner.nextLine();
            System.out.print("🏠 Enter the new address for the client: ");
            String address = scanner.nextLine();
            System.out.print("📞 Enter the new phone number for the client: ");
            String phoneNumber = scanner.nextLine();
            System.out.print("🛠️ Is the client professional? (true/false): ");
            boolean status = scanner.nextBoolean();
            scanner.nextLine();  // Consume the newline

            client.setName(name);
            client.setAddress(address);
            client.setPhone(phoneNumber);
            client.setProfessional(status);

            Client updatedClient = clientService.update(client);
            System.out.println("\n✅ Client updated successfully!\n");
            System.out.println(drawClientTable(updatedClient));
        } else {
            System.out.println("❌ Client not found.");
        }
    }

    public void deleteClient() {
        System.out.println("\n" + drawTableHeader("🔍 Delete a Client 🔍"));
        System.out.print("🆔 Enter the ID of the client: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Optional<Client> optionalClient = clientService.findById(id);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            System.out.println("\n" + drawTableHeader("🎉 Client found! 🎉"));
            System.out.println(drawClientTable(client));

            System.out.print("👉 Are you sure you want to delete this client? (y/n): ");
            String choice = scanner.nextLine().trim().toLowerCase();
            if (choice.equals("y")) {
                clientService.delete(client.getId());
                System.out.println("✅ Client deleted successfully!");
            } else if (choice.equals("n")) {
                System.out.println("🚫 Operation cancelled.");
            } else {
                System.out.println("⚠️ Invalid choice. Please enter 'y' or 'n'.");
            }
        } else {
            System.out.println("❌ Client not found.");
        }
    }

    // Helper method to draw a simple table header
    private String drawTableHeader(String title) {
        return "+---------------------------------------------+\n" +
                "| " + String.format("%-43s", title) + "|\n" +
                "+---------------------------------------------+";
    }

    // Helper method to draw a table row
    private String drawTableRow(String content) {
        return "| " + String.format("%-43s", content) + " |";
    }

    // Helper method to close the table
    private String drawTableFooter() {
        return "+---------------------------------------------+";
    }

    // Method to display client information in a table format
    private String drawClientTable(Client client) {
        return drawTableHeader("👤 Client Information") + "\n" +
                drawTableRow("📛 Name: " + client.getName()) + "\n" +
                drawTableRow("🏠 Address: " + client.getAddress()) + "\n" +
                drawTableRow("📞 Phone: " + client.getPhone()) + "\n" +
                drawTableRow("🛠️ Professional: " + (client.isProfessional() ? "Yes" : "No")) + "\n" +
                drawTableFooter();
    }
}
