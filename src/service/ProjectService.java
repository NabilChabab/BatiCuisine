package service;

import domain.entities.Project;
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

    public boolean delete(int id) {
        return projectRepository.delete(id);
    }

    public Project update(Project project) {
        return this.projectRepository.update(project);
    }

    public List<Project> findAll() {
        return this.projectRepository.findAll();
    }

    public Optional<Project> findById(int id) {
        return this.projectRepository.findById(id);
    }

    public Project findProjectByName(String name) {
        return projectRepository.findByName(name);
    }

    public void updateProjectFields(int projectId , double profitMargin , double totalCost){
        projectRepository.updateProject(projectId, profitMargin , totalCost);
    }

    public boolean updateProjectStatus(int projectId , String status){
        return projectRepository.updateStatus(projectId,status);
    }


}
