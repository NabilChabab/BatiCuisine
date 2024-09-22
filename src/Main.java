import repository.*;
import service.*;
import ui.*;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {


        String BLUE = "\u001B[34m";
        String RESET = "\u001B[0m";

        System.out.println(BLUE + "                                                                                           ");
        System.out.println("  ____        _  _____  ___    ____ _   _ ___ ____ ___ _   _ _____ ");
        System.out.println(" | __ )  __ _| ||_   _|_ _|  / ___| | | |_ _/ ___|_ _| \\ | | ____|");
        System.out.println(" |  _ \\ / _` | __|| |  | |  | |   | | | || |\\___ \\| ||  \\| |  _|  ");
        System.out.println(" | |_) | (_| | |_ | |  | |  | |___| |_| || | ___) | || |\\  | |___ ");
        System.out.println(" |____/ \\__,_|\\__||_| |___|  \\____|\\___/|___|____/___|_| \\_|_____|");
        System.out.println("                                                                   " + RESET);


        ClientRepository clientRepository = new ClientRepository();
        ComponentRepository componentRepository = new ComponentRepository();
        WorkForceRepository workForceRepository = new WorkForceRepository(componentRepository);
        MaterialRepository materialRepository = new MaterialRepository(componentRepository);
        ProjectRepository projectRepository = new ProjectRepository();
        ProjectService projectService = new ProjectService(projectRepository);
        ClientService clientService = new ClientService(clientRepository);
        ClientMenu clientMenu = new ClientMenu(clientService);

        MaterialService materialService = new MaterialService(materialRepository , componentRepository);
        ComponentService componentService = new ComponentService(componentRepository);
        MaterialMenu materialMenu = new MaterialMenu(materialService,componentService);
        WorkForceService workForceService = new WorkForceService(workForceRepository , componentRepository);
        WorkForceMenu workForceMenu = new WorkForceMenu(workForceService,componentService);
        ProjectMenu projectMenu = new ProjectMenu(projectService,clientMenu,materialMenu, workForceMenu);
        DevisRepository devisRepository = new DevisRepository();
        DevisService devisService = new DevisService(devisRepository);
        DevisMenu devisMenu = new DevisMenu(devisService,projectService);
        CostMenu costMenu = new CostMenu(projectRepository , componentRepository, materialService , workForceService , devisService, devisMenu);
        PrincipalMenu principalMenu = new PrincipalMenu(projectMenu , devisMenu , costMenu);
        principalMenu.Menu();
    }
}
