package repository.interfaces;



import domain.entities.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectInterface extends CrudInterface<Project>{
    @Override
    public Project save(Project entity);

    @Override
    public Optional<Project> findById(int project);

    @Override
    public List<Project> findAll();

    @Override
    public Project update(Project entity);

    @Override
    public boolean delete(Project entity);

    public Project findProjectByName(String name);

}
