package repository.interfaces;

import domain.entities.Component;

public interface ComponentInterface  <T extends Component> extends CrudInterface<Component>{

    double findTvaForComponent(int id);

}
