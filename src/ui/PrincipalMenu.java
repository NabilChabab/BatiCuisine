package ui;

import java.util.Scanner;

public class PrincipalMenu {

    private final ProjectMenu projectMenu;
    private static Scanner scanner;

    public PrincipalMenu(ProjectMenu projectMenu) {
        this.projectMenu = projectMenu;
        scanner = new Scanner(System.in);
    }

    public void Menu() {
        boolean check = true;

        while (check) {
            System.out.println("\n" + drawTableHeader("🏠 Kitchen Renovation Project Management 🛠️"));
            System.out.println(drawTableRow("1. ➕ Create a New Project"));
            System.out.println(drawTableRow("2. 📂 Display Existing Projects"));
            System.out.println(drawTableRow("3. 💰 Calculate Project Cost"));
            System.out.println(drawTableRow("4. 🚪 Quit"));
            System.out.println(drawTableFooter());
            System.out.print("👉 Please select an option (1-4): ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline

            switch (choice) {
                case 1:
                    ProjectAddMenu();
                    break;
                case 2:
                    oldProjectsMenu();
                    break;
                case 3:
                    totalCost();
                    break;
                case 4:
                    check = false;
                    System.out.println("👋 Exiting... Thank you for using the application!");
                    break;
                default:
                    System.out.println("⚠️ Invalid option. Please try again.");
                    break;
            }
        }
    }

    public void ProjectAddMenu() {
        System.out.println("\n" + drawTableHeader("➕ Create a New Project"));
        this.projectMenu.addOrSearchClientMenu();
    }

    public void oldProjectsMenu() {
        System.out.println("\n" + drawTableHeader("📂 Display Existing Projects"));
        this.projectMenu.findAll();
    }

    public void totalCost() {
        System.out.println("\n" + drawTableHeader("💰 Calculate Project Cost"));
        // Implementation for calculating total project cost goes here
    }

    // Helper method to draw a simple table header for better presentation
    private String drawTableHeader(String title) {
        return "+---------------------------------------------+\n" +
                "| " + String.format("%-43s", title) + "|\n" +
                "+---------------------------------------------+";
    }

    // Helper method to draw a row inside the table
    private String drawTableRow(String content) {
        return "| " + String.format("%-43s", content) + " |";
    }

    // Helper method to close the table after rows
    private String drawTableFooter() {
        return "+---------------------------------------------+";
    }
}
