package service;

import domain.entities.Client;
import domain.entities.Project;
import repository.ProjectRepository;

import java.util.List;
import java.util.Optional;

public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    public Project save(Project project) {
        return this.projectRepository.save(project);
    }

    public Boolean delete(Project project) {
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

    public void saveClientProject(Client client, Project project) {
        this.projectRepository.saveClientProject(client,project);
    }
}
