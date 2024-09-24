package ui;

import domain.entities.Material;
import domain.entities.Project;
import service.ComponentService;
import service.MaterialService;


import java.util.Optional;
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



    public void displayMenu() {
        while (true) {
            System.out.println("\n" + drawTableHeader("🏗️ Material Management Menu"));
            System.out.println(drawTableRow("🆕 1. Add a New Material"));
            System.out.println(drawTableRow("🔍 2. Find a Material"));
            System.out.println(drawTableRow("🚪 3. Update a Material"));
            System.out.println(drawTableRow("🚪 4. Delete a Material"));
            System.out.println(drawTableRow("🚪 5. Exit"));
            System.out.println(drawTableFooter());
            System.out.print("\n👉 Enter your choice (1-5): ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addMaterial(new Project());
                    break;
                case 2:
                    findMaterialById();
                    break;
                case 3:
                    updateMaterial();
                    break;
                case 4:
                    deleteMaterial();
                    break;
                case 5:
                    System.out.println("👋 Exiting...");
                    return;
                default:
                    System.out.println("⚠️ Invalid choice. Please try again.");
            }
        }
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


    public void findMaterialById() {
        System.out.println("\n" + drawTableHeader("🔍 Find a Material 🔍"));
        System.out.print("🆔 Enter the ID of the material: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Optional<Material> material = materialService.findById(id);
        if (material.isPresent()) {
            System.out.println("🎉 Material found! 🎉\n");
            System.out.println(drawMaterialTable(material.orElse(null)));
        } else {
            System.out.println("❌ Material not found.");
        }
    }

    public void updateMaterial() {
        System.out.println("\n" + drawTableHeader("🚪 Update a Material 🚪"));
        System.out.print("🆔 Enter the ID of the material to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Optional<Material> material = materialService.findById(id);
        if (material.isPresent()) {
            System.out.println("🎉 Material found! 🎉\n");
            System.out.println(drawMaterialTable(material.orElse(null)));

            System.out.print("📛 Enter the new name of the material: ");
            String name = scanner.nextLine();

            System.out.print("📦 Enter the new quantity of this material: ");
            double quantity = scanner.nextDouble();

            System.out.print("💰 Enter the new unit cost of the material (€/m² or €/litre): ");
            double unitCost = scanner.nextDouble();

            System.out.print("🚚 Enter the new transport cost of the material (€): ");
            double transportCost = scanner.nextDouble();

            System.out.print("🔧 Enter the new quality coefficient of the material (1.0 = standard, > 1.0 = high quality): ");
            double coefficientQuality = scanner.nextDouble();

            System.out.print("📊 Enter the new VAT rate of the material: ");
            double vatRate = scanner.nextDouble();
            scanner.nextLine();

            material.get().setName(name);
            material.get().setQuantity(quantity);
            material.get().setUnitCost(unitCost);
            material.get().setTransportCost(transportCost);
            material.get().setCoefficientQuality(coefficientQuality);
            material.get().setVatRate(vatRate);

            Material updatedMaterial = materialService.update(material.orElse(null));
            if (updatedMaterial != null) {
                System.out.println("\n✅ Material updated successfully!\n");
                System.out.println(drawMaterialTable(updatedMaterial));
            } else {
                System.out.println("Error updating material.");
            }
        } else {
            System.out.println("❌ Material not found.");
        }
    }


    public void deleteMaterial() {
        System.out.println("\n" + drawTableHeader("🚪 Delete a Material 🚪"));
        System.out.print("🆔 Enter the ID of the material to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Optional<Material> material = materialService.findById(id);
        if (material.isPresent()) {
            System.out.println("🎉 Material found! 🎉\n");
            System.out.println(drawMaterialTable(material.orElse(null)));

            System.out.print("👉 Are you sure you want to delete this material? (y/n): ");
            String confirm = scanner.nextLine().trim().toLowerCase();

            if (confirm.equals("y")) {
                boolean deleted = materialService.delete(material.get().getId());
                if (deleted) {
                    System.out.println("\n✅ Material deleted successfully!\n");
                } else {
                    System.out.println("Error deleting material.");
                }
            } else {
                System.out.println("❌ Deletion cancelled.");
            }
        } else {
            System.out.println("❌ Material not found.");
        }
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
