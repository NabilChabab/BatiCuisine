package service.interfaces;

import domain.entities.Client;
import domain.entities.Material;
import domain.entities.Project;
import domain.entities.WorkForce;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    Project save(Project project);

    boolean delete(Project project);

    Project update(Project project);

    List<Project> findAll();

    Optional<Project> findById(int project);

    Project findProjectByName(String name);

}
