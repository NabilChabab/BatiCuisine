package ui;

import domain.entities.Project;
import domain.entities.WorkForce;
import service.ComponentService;
import service.WorkForceService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class WorkForceMenu {
    private final WorkForceService workForceService;
    private final ComponentService componentService;
    private final Scanner scanner;

    public WorkForceMenu(WorkForceService workForceService, ComponentService componentService) {
        this.workForceService = workForceService;
        this.componentService = componentService;
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        while (true) {
            System.out.println("\n" + drawTableHeader("👷 Workforce Management Menu"));
            System.out.println(drawTableRow("🆕 1. Add a New Workforce"));
            System.out.println(drawTableRow("🔍 2. Find a Workforce"));
            System.out.println(drawTableRow("🔄 3. Update a Workforce"));
            System.out.println(drawTableRow("🗑️ 4. Delete a Workforce"));
            System.out.println(drawTableRow("🚪 5. Exit"));
            System.out.println(drawTableFooter());
            System.out.print("\n👉 Enter your choice (1-5): ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addWorkForce(new Project());
                    break;
                case 2:
                    findWorkForceById();
                    break;
                case 3:
                    updateWorkForce();
                    break;
                case 4:
                    deleteWorkForce();
                    break;
                case 5:
                    System.out.println("👋 Exiting...");
                    return;
                default:
                    System.out.println("⚠️ Invalid choice. Please try again.");
            }
        }
    }

    public WorkForce addWorkForce(Project project) {
        String continueChoice;
        WorkForce workForce = null;

        do {
            System.out.println("\n" + drawTableHeader("🔨 Add Workforce 🔨"));

            System.out.print("👷 Enter the name of the workforce: ");
            String name = scanner.nextLine();

            System.out.print("📊 Enter the VAT rate of the workforce: ");
            double vatRate = scanner.nextDouble();
            scanner.nextLine();  // Consume newline

            System.out.print("💰 Enter the hourly rate for this labor (€/h): ");
            double hourlyRate = scanner.nextDouble();
            scanner.nextLine();  // Consume newline

            System.out.print("⏱️ Enter the number of hours worked: ");
            double hoursWorked = scanner.nextDouble();
            scanner.nextLine();  // Consume newline

            System.out.print("🔧 Enter the productivity factor (1.0 = standard, > 1.0 = high productivity): ");
            double productivityFactor = scanner.nextDouble();
            scanner.nextLine();


            // Create and save the workforce
            workForce = new WorkForce(0, name, "workforce", vatRate, project, hourlyRate, hoursWorked, productivityFactor);
            if (workForceService.save(workForce) != null) {
                System.out.println("\n✅ Workforce added successfully!\n");
                System.out.println(drawWorkforceTable(workForce));
            } else {
                System.out.println("Error saving workforce.");
            }

            System.out.print("👉 Would you like to add another workforce? (y/n): ");
            continueChoice = scanner.nextLine().trim().toLowerCase();

        } while (continueChoice.equals("y"));

        return workForce;
    }

    public void findWorkForceById() {
        System.out.print("🔍 Enter the ID of the workforce: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        findById(id);
    }

    public void updateWorkForce() {
        System.out.print("🔄 Enter the ID of the workforce to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        Optional<WorkForce> foundWorkForce = this.workForceService.findById(id);
        foundWorkForce.ifPresentOrElse(
                workForce -> {
                    System.out.println("\n" + drawWorkforceTable(workForce));
                    System.out.println("\n🔧 **Update Workforce** 🔧");
                    System.out.print("👷 Enter the name of the workforce: ");
                    String name = scanner.nextLine();

                    System.out.print("📊 Enter the VAT rate of the workforce: ");
                    double vatRate = scanner.nextDouble();
                    scanner.nextLine();  // Consume newline

                    System.out.print("💰 Enter the hourly rate for this labor (€/h): ");
                    double hourlyRate = scanner.nextDouble();
                    scanner.nextLine();  // Consume newline

                    System.out.print("⏱️ Enter the number of hours worked: ");
                    double hoursWorked = scanner.nextDouble();
                    scanner.nextLine();  // Consume newline

                    System.out.print("🔧 Enter the productivity factor (1.0 = standard, > 1.0 = high productivity): ");
                    double productivityFactor = scanner.nextDouble();
                    scanner.nextLine();

                    WorkForce updatedWorkForce = new WorkForce(id, name, "workforce", vatRate, workForce.getProject(), hourlyRate, hoursWorked, productivityFactor);
                    update(updatedWorkForce);
                },
                () -> System.out.println("❌ Workforce not found.")
        );
    }


    public void deleteWorkForce() {
        System.out.print("🗑️ Enter the ID of the workforce to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        delete(id);
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

    // Method to display workforce information in a table format
    private String drawWorkforceTable(WorkForce workForce) {
        return drawTableHeader("👷 Workforce Information") + "\n" +
                drawTableRow("👷 Name: " + workForce.getName()) + "\n" +
                drawTableRow("📊 VAT Rate: " + workForce.getVatRate() + "%") + "\n" +
                drawTableRow("💰 Hourly Rate: €" + workForce.getHourlyCost()) + "\n" +
                drawTableRow("⏱️ Hours Worked: " + workForce.getWorkingHours()) + "\n" +
                drawTableRow("🔧 Productivity Factor: " + workForce.getWorkerProductivity()) + "\n" +
                drawTableFooter();
    }

    public void update(WorkForce workForce) {
        this.workForceService.update(workForce);
        System.out.println("🔄 Workforce updated successfully.");
    }

    public void delete(int workForce) {
        this.workForceService.delete(workForce);
        System.out.println("🗑️ Workforce deleted successfully.");
    }

    public void findById(int workForce) {
        Optional<WorkForce> foundWorkForce = this.workForceService.findById(workForce);
        foundWorkForce.ifPresentOrElse(
                wf -> System.out.println(drawWorkforceTable(wf)),
                () -> System.out.println("❌ Workforce not found.")
        );
    }

    public void findAll() {
        List<WorkForce> workForces = this.workForceService.findAll();
        if (!workForces.isEmpty()) {
            System.out.println("\n📋 **All Workforce Entries** 📋");
            for (WorkForce workForce : workForces) {
                System.out.println(drawWorkforceTable(workForce));
            }
        } else {
            System.out.println("No workforce entries available.");
        }
    }
}
