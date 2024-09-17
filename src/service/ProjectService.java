package service;



import domain.entities.Client;
import domain.entities.Material;
import domain.entities.Project;
import domain.entities.WorkForce;
import repository.*;
import utils.Validations;

import java.util.List;
import java.util.Optional;

public class ProjectService{
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    public Project save(Project project) {
        Validations.projectValidation(project);
        return this.projectRepository.save(project);
    }

    public boolean delete(Project project) {
        return projectRepository.delete(project);
    }

    public Project update(Project project) {
        return this.projectRepository.update(project);
    }

    public List<Project> findAll() {
        return this.projectRepository.findAll();
    }

    public Optional<Project> findById(Project project) {
        return this.projectRepository.findById(project);
    }

    public void saveClientProject(Client client, Project project, Material material, WorkForce workForce) {
        this.projectRepository.saveClientProject(client, project,material, workForce);
    }

}
