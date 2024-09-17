import config.Database;
import repository.*;
import service.*;
import ui.*;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {



        ClientRepository clientRepository = new ClientRepository();
        ComponentRepository componentRepository = new ComponentRepository();
        WorkForceRepository workForceRepository = new WorkForceRepository(componentRepository);
        MaterialRepository materialRepository = new MaterialRepository(componentRepository);
        ProjectRepository projectRepository = new ProjectRepository(clientRepository,componentRepository,materialRepository,workForceRepository);
        ProjectService projectService = new ProjectService(projectRepository);
        ClientService clientService = new ClientService(clientRepository);
        ClientMenu clientMenu = new ClientMenu(clientService);
        MaterialService materialService = new MaterialService(materialRepository);
        ComponentService componentService = new ComponentService(componentRepository);
        MaterialMenu materialMenu = new MaterialMenu(materialService,componentService);
        WorkForceService workForceService = new WorkForceService(workForceRepository);
        WorkForceMenu workForceMenu = new WorkForceMenu(workForceService,componentService);
        ProjectMenu projectMenu = new ProjectMenu(projectService,clientMenu,materialMenu, workForceMenu);
        PrincipalMenu principalMenu = new PrincipalMenu(projectMenu);
        principalMenu.Menu();
    }
}
