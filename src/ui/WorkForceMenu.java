package ui;

import domain.entities.Component;
import domain.entities.Project;
import domain.entities.WorkForce;
import domain.enums.ComponentType;
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
            scanner.nextLine();  // Consume newline

            // Create and save the component
            Component component = new Component();
            component.setName(name);
            component.setComponentType(ComponentType.WORKFORCE.name());
            component.setVatRate(vatRate);
            component.setProject(project);

            Component savedComponent = componentService.save(component);

            // Create and save the workforce
            workForce = new WorkForce(0, name, "workforce", vatRate, project, hourlyRate, hoursWorked, productivityFactor, savedComponent);
            workForceService.save(workForce);

            System.out.println("\n✅ Workforce added successfully!\n");
            System.out.println(drawWorkforceTable(workForce));

            System.out.print("👉 Would you like to add another workforce? (y/n): ");
            continueChoice = scanner.nextLine().trim().toLowerCase();

        } while (continueChoice.equals("y"));

        return workForce;
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

    // Methods for updating, deleting, finding, and listing all workforce entries
    public void update(WorkForce workForce) {
        this.workForceService.update(workForce);
        System.out.println("🔄 Workforce updated successfully.");
    }

    public void delete(WorkForce workForce) {
        this.workForceService.delete(workForce);
        System.out.println("🗑️ Workforce deleted successfully.");
    }

    public void findById(WorkForce workForce) {
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
