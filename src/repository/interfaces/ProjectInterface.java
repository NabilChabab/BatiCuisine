package repository.interfaces;



import domain.entities.Project;

public interface ProjectInterface extends CrudInterface<Project>{
    void updateProject(int id , double marginProfit , double totalCost);
    boolean updateStatus(int id, String status);
    Project findByName(String name);

}
