package utils;


import domain.entities.Client;
import domain.entities.Devis;
import domain.entities.Project;
import exceptions.ClientNotFoundException;
import exceptions.DevisNotFoundException;
import exceptions.ProjectsNotFoundException;

public class Validations {

    public static void projectValidation(Project project) {
        if (project.getProjectName() == null || project.getProjectName().isEmpty()) {
            throw new ProjectsNotFoundException("Project name cannot be null or empty");
        }
        if (project.getProfitMargin() < 0) {
            throw new ProjectsNotFoundException("ProfitMargin cannot be negative");
        }
        if (project.getStatus() == null) {
            throw new ProjectsNotFoundException("Status cannot be null");
        }

        if (project.getTotalCost() < 0) {
            throw new ProjectsNotFoundException("Total cost cannot be negative");
        }

        if (project.getClient() == null || project.getClient().getId() <= 0) {
            throw new ProjectsNotFoundException("Client cannot be null or empty");
        }
    }

    public static void clientValidation(Client client) {
        if (client.getName() == null || client.getName().isEmpty()) {
            throw new ClientNotFoundException("Client name cannot be null or empty");
        }
        if (client.getAddress() == null || client.getAddress().isEmpty()) {
            throw new ClientNotFoundException("Address cannot be null or empty");
        }
        if (client.getPhone() == null || client.getPhone().isEmpty()) {
            throw new ClientNotFoundException("Phone cannot be null or empty");
        }
    }

    public static void devisValidation(Devis devis) {
        if (devis.getEstimatedAmount() > 0) {
            throw new DevisNotFoundException("Estimated amount cannot be greater than zero");
        }
        if (devis.getIssueDate() == null) {
            throw new DevisNotFoundException("IssueDate cannot be null");
        }
    }

    public static void ClientByNameValidation(String name){
        if(name == null || name.isEmpty()){
            throw new ClientNotFoundException("Client name cannot be null or empty");
        }
    }
}
