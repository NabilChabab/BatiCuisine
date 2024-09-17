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
