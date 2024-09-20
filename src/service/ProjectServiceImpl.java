package service;



import domain.entities.Client;
import domain.entities.Material;
import domain.entities.Project;
import domain.entities.WorkForce;
import repository.*;
import service.interfaces.ProjectService;
import utils.Validations;

import java.util.List;
import java.util.Optional;

public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    @Override
    public Project save(Project project) {
        Validations.projectValidation(project);
        return this.projectRepository.save(project);
    }

    @Override
    public boolean delete(Project project) {
        return projectRepository.delete(project);
    }

    @Override
    public Project update(Project project) {
        return this.projectRepository.update(project);
    }

    @Override
    public List<Project> findAll() {
        return this.projectRepository.findAll();
    }

    @Override
    public Optional<Project> findById(int project) {
        return this.projectRepository.findById(project);
    }


    @Override
    public Project findProjectByName(String name) {
        return this.projectRepository.findProjectByName(name);
    }


}
