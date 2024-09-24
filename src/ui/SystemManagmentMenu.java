package ui;

import java.util.Scanner;

public class SystemManagmentMenu {


    private final ClientMenu clientMenu;
    private final ComponentMenu componentMenu;
    private final DevisMenu devisMenu;
    private static final Scanner scanner = new Scanner(System.in);

    public SystemManagmentMenu(ClientMenu clientMenu, ComponentMenu componentMenu, DevisMenu devisMenu) {
        this.clientMenu = clientMenu;
        this.componentMenu = componentMenu;
        this.devisMenu = devisMenu;
    }


    public void displayMenu() {
        boolean check = true;

        while (check) {
            System.out.println("\n" + drawTableHeader("🔧 System Management Menu"));
            System.out.println(drawTableRow("1. 📑 Devis Management"));
            System.out.println(drawTableRow("2. 👤 Client Management"));
            System.out.println(drawTableRow("3. 🛠️ Component Management"));
            System.out.println(drawTableRow("4. 🚪 Exit"));
            System.out.println(drawTableFooter());
            System.out.print("👉 Please select an option (1-4): ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice) {
                case 1:
                    devisMenu.displayMenu();
                    break;
                case 2:
                    clientMenu.displayMenu();
                    break;
                case 3:
                    componentMenu.displayMenu();
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

    private String drawTableHeader(String title) {
        return "+---------------------------------------------+\n" +
                "| " + String.format("%-43s", title) + "|\n" +
                "+---------------------------------------------+";
    }

    private String drawTableRow(String row) {
        return "| " + String.format("%-43s", row) + "|";
    }

    private String drawTableFooter() {
        return "+---------------------------------------------+";
    }


}
