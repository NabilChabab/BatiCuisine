package ui;

import domain.entities.Client;
import domain.entities.Project;
import domain.enums.ProjectStatus;
import service.ProjectService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ProjectMenu {

    private final ProjectService projectService;
    private final ClientMenu clientMenu;
    private final Scanner scanner;
    private Client selectedClient;
    private final MaterialMenu materialMenu;
    private final WorkForceMenu workForceMenu;

    private static final int TABLE_WIDTH = 170;
    private static final String BLUE = "\u001B[34m";
    private static final String RESET = "\u001B[0m";

    public ProjectMenu(ProjectService projectService, ClientMenu clientMenu, MaterialMenu materialMenu, WorkForceMenu workForceMenu) {
        this.projectService = projectService;
        this.clientMenu = clientMenu;
        this.materialMenu = materialMenu;
        this.workForceMenu = workForceMenu;
        this.scanner = new Scanner(System.in);
    }

    public void addOrSearchClientMenu() {
        while (true) {
            System.out.println("\n" + drawTableHeader("🌟  Client Management Menu 🌟"));
            System.out.println(drawTableRow("👤 1. Search for an Existing Client"));
            System.out.println(drawTableRow("🆕 2. Add a New Client"));
            System.out.println(drawTableRow("🚪 3. Exit"));
            System.out.println(drawTableFooter());
            System.out.print("\n👉 Enter your choice (1-3): ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    searchClient();
                    break;
                case 2:
                    addNewClient();
                    break;
                case 3:
                    System.out.println("👋 Exiting...");
                    return;
                default:
                    System.out.println("⚠️ Invalid choice. Please try again.");
            }
        }
    }

    private void searchClient() {
        System.out.println("\n" + drawTableHeader("🔍 Search for an Existing Client 🔍"));
        System.out.print("👤 Enter the name of the client: ");
        String name = scanner.nextLine();
        Client optionalClient = clientMenu.searchByName(name);

        if (optionalClient != null) {
            selectedClient = optionalClient;
            System.out.println("🎉 Client found! 🎉\n");
            System.out.println(drawClientTable(selectedClient));
            addProject();
        } else {
            System.out.println("❌ Client not found.");
            selectedClient = null;
        }
    }

    private void addNewClient() {
        System.out.println("\n" + drawTableHeader("🆕 Add a New Client ✏️"));
        selectedClient = clientMenu.addNewClient();

        if (selectedClient != null) {
            System.out.println("🎉 Client added successfully!\n");
            System.out.println(drawClientTable(selectedClient));
            addProject();
        } else {
            System.out.println("❌ Failed to add new client.");
        }
    }

    private void addProject() {
        try {
            System.out.println("\n" + drawTableHeader("🔨 Add a New Project 🔨"));
            System.out.print("🏗️ Enter the name of the project: ");
            String name = scanner.nextLine();
            System.out.print("📏 Enter the surface area for the project (in sq meters): ");
            double surface = scanner.nextDouble();
            scanner.nextLine();

            Project project = new Project(0, name, 0, 0, ProjectStatus.INPROGRESS.name(), surface, selectedClient);
            Project savedProject = projectService.save(project);

            materialMenu.addMaterial(savedProject);
            workForceMenu.addWorkForce(savedProject);

            System.out.println("✅ Project added successfully!\n");
            System.out.println(drawProjectTable(savedProject));
        } catch (Exception e) {
            System.out.println("⚠️ An error occurred while adding the project: " + e.getMessage());
        }
    }

    public void findAll() {
        List<Project> projects = projectService.findAll();
        System.out.println("\n📜 All Projects 📜");
        printTableHeader();
        projects.stream()
                .distinct()
                .forEach(this::printProjectRow);
        printTableFooter();
    }

//    public void findAll() {
//        List<Project> projects = projectService.findAll();
//        System.out.println("\n📜 All Projects 📜");
//
//        Map<Integer, Project> projectMap = new HashMap<>();
//
//        for (Project project : projects) {
//            if (!projectMap.containsKey(project.getId())) {
//                projectMap.put(project.getId(), project);
//            } else {
//                Project existingProject = projectMap.get(project.getId());
//                project.getComponents().forEach(existingProject::addComponent);
//            }
//        }
//
//        printTableHeader();
//
//        projectMap.values().forEach(this::printProjectRow);
//
//        printTableFooter();
//    }

    private void printTableHeader() {
        System.out.println(BLUE);
        printLine( '=');
        System.out.printf(BLUE+"| %-4s | %-20s | %-8s | %-10s | %-12s | %-10s | %-20s | %-12s | %-20s | %-25s |\n",
                "ID", "Name", "Surface", "Status", "Profit Margin", "Total Cost", "Client Name", "Client Phone", "Client Address", "Components");
        printLine('-');
    }

    private void printProjectRow(Project project) {
        Client client = project.getClient();
        String clientName = client != null ? truncate(client.getName(), 20) : "N/A";
        String clientPhone = client != null ? truncate(client.getPhone(), 12) : "N/A";
        String clientAddress = client != null ? truncate(client.getAddress(), 20) : "N/A";

        String components = project.getComponents().stream()
                .map(c -> c.getName() + "(" + c.getComponentType() + ")")
                .collect(Collectors.joining(", "));

        System.out.printf("| %-4d | %-20s | %-8s | %-10s | %-12s | %-10s | %-20s | %-12s | %-20s | %-25s |\n",
                project.getId(),
                truncate(project.getProjectName(), 20),
                truncate(String.valueOf(project.getSurface()), 8),
                truncate(String.valueOf(project.getStatus()), 10),
                truncate(String.valueOf(project.getProfitMargin()), 12),
                truncate(String.valueOf(project.getTotalCost()), 10),
                clientName,
                clientPhone,
                clientAddress,
                truncate(components, 25));

        // If components don't fit in one line, print them on subsequent lines
        if (components.length() > 25) {
            for (String line : splitIntoLines(components.substring(25), 25)) {
                System.out.printf("| %-4s | %-20s | %-8s | %-10s | %-12s | %-10s | %-20s | %-12s | %-20s | %-25s |\n",
                        "", "", "", "", "", "", "", "", "", line);
            }
        }
        printLine('-');
    }

    private void printTableFooter() {
        printLine('=');
        System.out.println(RESET);
    }

    private void printLine(char c) {
        System.out.println(String.format("%" + TABLE_WIDTH + "s", "").replace(' ', c));
    }

    private String truncate(String input, int maxLength) {
        if (input == null) return "N/A";
        return input.length() > maxLength ? input.substring(0, maxLength - 3) + "..." : input;
    }

    private String[] splitIntoLines(String input, int maxLength) {
        return input.split("(?<=\\G.{" + maxLength + "})");
    }



    // Method to draw a simple table header for better presentation
    private String drawTableHeader(String title) {
        return "+---------------------------------------------+\n" +
                "| " + String.format("%-43s", title) + "|\n" +
                "+---------------------------------------------+";
    }

    // Method to draw a row inside the table
    private String drawTableRow(String content) {
        return "| " + String.format("%-43s", content) + " |";
    }


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

    // Method to display project information in a table format
    private String drawProjectTable(Project project) {
        return drawTableHeader("🏗️ Project Information") + "\n" +
                drawTableRow("🏗️ Name: " + project.getProjectName()) + "\n" +
                drawTableRow("📊 Status: " + project.getStatus()) + "\n" +
                drawTableRow("📏 Surface Area: " + project.getSurface()) + "\n" +
                drawTableRow("👤 Client: " + project.getClient().getName()) + "\n" +
                drawTableFooter();
    }
}
