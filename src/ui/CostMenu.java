package ui;

import domain.entities.Devis;
import domain.entities.Material;
import domain.entities.Project;
import domain.entities.WorkForce;
import domain.enums.ProjectStatus;
import exceptions.DevisNotFoundException;
import repository.ComponentRepository;
import repository.ProjectRepository;
import service.DevisService;
import service.MaterialService;
import service.WorkForceService;

import utils.DateFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class CostMenu {


    private static Scanner scanner;
    private final ProjectRepository projectRepository;
    private final ComponentRepository componentRepository;
    private final MaterialService materialService;
    private final WorkForceService workForceService;
    private final DevisService devisService;
    private final DevisMenu devisMenu;


    public CostMenu(ProjectRepository projectRepository, ComponentRepository componentRepository, MaterialService materialService, WorkForceService workForceService, DevisService devisService, DevisMenu devisMenu) {
        this.devisService = devisService;
        this.devisMenu = devisMenu;
        scanner = new Scanner(System.in);
        this.projectRepository = projectRepository;
        this.componentRepository = componentRepository;
        this.materialService = materialService;
        this.workForceService = workForceService;
    }

    private static boolean getYesNoInput(String prompt) {
        System.out.print(prompt);
        String input = scanner.next().trim().toLowerCase();
        return input.equals("y") || input.equals("yes");
    }

    public void save() {
        System.out.println("--- Total Cost Calculation ---");

        System.out.print("Enter project ID: ");
        int projectId = scanner.nextInt();

        Project project = projectRepository.findById(projectId).orElseThrow(() ->
                new RuntimeException("Project not found"));


        List<Material> materials = materialService.findAllByProjectId(projectId);
        List<WorkForce> workforce = workForceService.findAllByProjectId(projectId);

        double totalMaterialBeforeVat = 0;
        double totalMaterialAfterVat = 0;

        for (Material material : materials) {
            double materialCostBeforeVat = materialService.calculateMaterialBeforeVatRate(material);
            double materialCostAfterVat = materialService.calculateMaterialAfterVatRate(material);

            totalMaterialBeforeVat += materialCostBeforeVat;
            totalMaterialAfterVat += materialCostAfterVat;
        }

        double totalWorkforceBeforeVat = 0;
        double totalWorkforceAfterVat = 0;

        for (WorkForce workForce : workforce) {
            double workforceCostBeforeVat = workForceService.calculateWorkforceBeforeVat(workForce);
            double workforceCostAfterVat = workForceService.calculateWorkforceAfterVat(workForce);

            totalWorkforceBeforeVat += workforceCostBeforeVat;
            totalWorkforceAfterVat += workforceCostAfterVat;
        }

        double totalCostBeforeMargin = totalMaterialBeforeVat + totalWorkforceBeforeVat;
        double totalCostAfterVat = totalMaterialAfterVat + totalWorkforceAfterVat;

        double totalCost = totalCostAfterVat;
        double marginRate = 0.0;
        if (getYesNoInput("Do you want to apply a profit margin to the project? (y/n): ")) {
            System.out.print("Enter profit margin percentage: ");
            marginRate = scanner.nextDouble();
            scanner.nextLine();
            project.setProfitMargin(marginRate);
            double profitMargin = totalCost * marginRate / 100;
            totalCost += profitMargin;
        }


        projectRepository.updateProject(projectId, project.getProfitMargin(), totalCost);


        System.out.println("\n--- Calculation Result ---");
        System.out.println("Project Name: " + project.getProjectName());
        System.out.println("Client: " + project.getClient().getName());
        System.out.println("Address: " + project.getClient().getAddress());
        System.out.println("Area: " + project.getSurface() + " m²");
        System.out.println("--- Cost Details ---");
        System.out.println("Materials Cost Before VAT: " + String.format("%.2f", totalMaterialBeforeVat) + " €");
        System.out.println("Materials Cost After VAT: " + String.format("%.2f", totalMaterialAfterVat) + " €");
        System.out.println("Workforce Cost Before VAT: " + String.format("%.2f", totalWorkforceBeforeVat) + " €");
        System.out.println("Workforce Cost After VAT: " + String.format("%.2f", totalWorkforceAfterVat) + " €");
        System.out.println("Total Cost Before Margin: " + String.format("%.2f", totalCostBeforeMargin) + " €");

        double discount = 0.0;
        if (project.getClient().isProfessional()) {
            System.out.println("\n--- Professional Client Discount Applied ---");
            System.out.println("Enter discount percentage: ");
            discount = scanner.nextDouble();
            scanner.nextLine();
            if (discount < 0 || discount > 100) {
                System.out.println("Invalid discount percentage. Please enter a value between 0 and 100.");
                return;
            }
            totalCost *= discount;
            System.out.println("Discounted Total Cost: " + String.format("%.2f", totalCost) + " €");
        }


        System.out.print("Enter issue date (yyyy-MM-dd): ");
        String issueDate = scanner.nextLine();
        LocalDate issueDateParse = DateFormat.parseDate(issueDate);
        System.out.print("Enter validated date (yyyy-MM-dd): ");
        String validatedDate = scanner.nextLine();
        LocalDate validatedDateParse = DateFormat.parseDate(validatedDate);
        Devis devis = new Devis(0, totalCost, issueDateParse, validatedDateParse, false, project);
        devisService.save(devis);


        System.out.print("Do you want to accept the devis? (Yes/No): ");
        String choice = scanner.nextLine().trim().toLowerCase();

        switch (choice) {
            case "yes":
            case "y":
                devisService.updateDevisStatus(devis.getId());
                projectRepository.updateStatus(projectId, ProjectStatus.FINISHED.name());
                System.out.println("Devis accepted. Project marked as FINISHED.");
                break;
            case "no":
            case "n":
                projectRepository.updateStatus(projectId, ProjectStatus.CANCELLED.name());
                System.out.println("Devis rejected. Project marked as CANCELLED.");
                break;
            default:
                System.out.println("Invalid choice. Please enter 'Yes' or 'No'.");
        }

        try {
            devisMenu.findDevisByProject(projectId);
        } catch (DevisNotFoundException devisNotFoundException) {
            System.out.println(devisNotFoundException.getMessage());
        }
    }
}
