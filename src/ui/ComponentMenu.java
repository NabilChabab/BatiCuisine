package ui;

import java.util.Scanner;

public class ComponentMenu {


    private final MaterialMenu materialMenu;
    private final WorkForceMenu workForceMenu;
    private final static Scanner scanner = new Scanner(System.in);

    public ComponentMenu(MaterialMenu materialMenu, WorkForceMenu workForceMenu) {
        this.materialMenu = materialMenu;
        this.workForceMenu = workForceMenu;
    }



    public void displayMenu() {
        System.out.println("\n" + drawTableHeader("🛠️ Component Management Menu"));
        System.out.println(drawTableRow("🆕 1. Manage Materials"));
        System.out.println(drawTableRow("🔍 2. Manage Workforces"));
        System.out.println(drawTableRow("🚪 3. Exit"));
        System.out.println(drawTableFooter());
        System.out.print("\n👉 Enter your choice (1-3): ");


        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                materialMenu.displayMenu();
                break;
            case 2:
                workForceMenu.displayMenu();
                break;
            case 3:
                System.out.println("👋 Exiting...");
                break;
            default:
                System.out.println("⚠️ Invalid choice. Please try again.");
        }
    }

    private String drawTableHeader(String title) {
        return "+---------------------------------------------+\n" +
                "| " + String.format("%-43s", title) + "|\n" +
                "+---------------------------------------------+";
    }

    private String drawTableRow(String row) {
        return "| " + String.format("%-45s", row) + "|";
    }

    private String drawTableFooter() {
        return "+---------------------------------------------+";
    }
}
