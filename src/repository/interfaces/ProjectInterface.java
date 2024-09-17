package repository.interfaces;



import domain.entities.Client;
import domain.entities.Material;
import domain.entities.Project;
import domain.entities.WorkForce;

import java.util.List;
import java.util.Optional;

public interface ProjectInterface extends CrudInterface<Project>{
    @Override
    public Project save(Project entity);

    @Override
    public Optional<Project> findById(Project project);

    @Override
    public List<Project> findAll();

    @Override
    public Project update(Project entity);

    @Override
    public boolean delete(Project entity);

    public void saveClientProject(Client client, Project project, Material material, WorkForce workForce);
}
