package repository.interfaces;

import domain.entities.Component;

import java.util.List;
import java.util.Optional;

public interface ComponentInterface  <T extends Component> extends CrudInterface<Component>{

    double findTvaForComponent(int id);

}
