package repository.interfaces;

import domain.entities.Material;

import java.util.List;
import java.util.Optional;

public interface MaterialInterface  <T extends Material> extends CrudInterface<Material>{
    List<Material> findAllByProjectId(int id);
}
