package domain.entities;

import domain.enums.ProjectStatus;

import java.util.ArrayList;
import java.util.List;

public class Project {
    private int id;
    private String projectName;
    private double profitMargin;
    private double totalCost;
    List<Component> listComponents;
    private ProjectStatus status;
    private Client client;

    public Project(int id, String projectname, double profitMargin, double totalCost,String status,Client client) {
        this.id = id;
        this.projectName = projectname;
        this.profitMargin = profitMargin;
        this.totalCost = totalCost;
        this.listComponents = listComponents;
        listComponents=new ArrayList<>();
        this.status=ProjectStatus.valueOf(status);
        this.client=client;

    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Project() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getprojectname() {
        return projectName;
    }

    public void setprojectname(String projectname) {
        this.projectName = projectname;
    }

    public double getprofitMargin() {
        return profitMargin;
    }

    public void setprofitMargin(double profitMargin) {
        this.profitMargin = profitMargin;
    }

    public double gettotalCost() {
        return totalCost;
    }

    public void settotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public List<Component> getListComponents() {
        return listComponents;
    }

    public void setListComponents(List<Component> listComponents) {
        this.listComponents = listComponents;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", projectname='" + projectName + '\'' +
                ", profitMargin=" + profitMargin +
                ", totalCost=" + totalCost +
                '}';
    }
}
