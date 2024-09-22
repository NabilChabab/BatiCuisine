package ui;

import domain.entities.Component;
import domain.entities.Material;
import domain.entities.Project;
import domain.enums.ComponentType;
import service.ComponentService;
import service.MaterialService;


import java.util.Scanner;

public class MaterialMenu {
    private final MaterialService materialService;
    private final ComponentService componentService;
    private final Scanner scanner;

    public MaterialMenu(MaterialService materialService, ComponentService componentService) {
        this.materialService = materialService;
        this.componentService = componentService;
        this.scanner = new Scanner(System.in);
    }

    public Material addMaterial(Project project) {
        String continueChoice;
        Material material = null;

        do {
            System.out.println("\n" + drawTableHeader("🏗️ Add Material 🏗️"));

            System.out.print("📛 Enter the name of the material: ");
            String name = scanner.nextLine();

            System.out.print("📦 Enter the quantity of this material: ");
            double quantity = scanner.nextDouble();

            System.out.print("💰 Enter the unit cost of the material (€/m² or €/litre): ");
            double unitCost = scanner.nextDouble();

            System.out.print("🚚 Enter the transport cost of the material (€): ");
            double transportCost = scanner.nextDouble();

            System.out.print("🔧 Enter the quality coefficient of the material (1.0 = standard, > 1.0 = high quality): ");
            double coefficientQuality = scanner.nextDouble();

            System.out.print("📊 Enter the VAT rate of the material: ");
            double vatRate = scanner.nextDouble();
            scanner.nextLine();

            material = new Material(0, name, "Material", vatRate, project, unitCost, quantity, transportCost, coefficientQuality);
            Material savedMaterial = materialService.save(material);
            if (savedMaterial != null) {
                System.out.println("\n✅ Material added successfully!\n");
                System.out.println(drawMaterialTable(material));
            } else {
                System.out.println("Error saving material.");
            }

            System.out.print("👉 Would you like to add another material? (y/n): ");
            continueChoice = scanner.nextLine().trim().toLowerCase();

        } while (continueChoice.equals("y"));

        return material;
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

    // Method to display material information in a table format
    private String drawMaterialTable(Material material) {
        return drawTableHeader("📋 Material Information") + "\n" +
                drawTableRow("📛 Name: " + material.getName()) + "\n" +
                drawTableRow("📦 Quantity: " + material.getQuantity()) + "\n" +
                drawTableRow("💰 Unit Cost: €" + material.getUnitCost()) + "\n" +
                drawTableRow("🚚 Transport Cost: €" + material.getTransportCost()) + "\n" +
                drawTableRow("🔧 Quality Coefficient: " + material.getCoefficientQuality()) + "\n" +
                drawTableRow("📊 VAT Rate: " + material.getVatRate() + "%") + "\n" +
                drawTableFooter();
    }
}
