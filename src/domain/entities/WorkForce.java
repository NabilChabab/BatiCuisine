package domain.entities;

public class WorkForce extends Component{

    private double hourlyCost;
    private double workingHours;
    private double workerProductivity;


    public WorkForce(String name, String componentType, double vatRate, double hourlyCost, double workingHours, double workerProductivity) {
        super(name, componentType, vatRate);
        this.hourlyCost = hourlyCost;
        this.workingHours = workingHours;
        this.workerProductivity = workerProductivity;
    }

    public WorkForce() {
    }

    public double getHourlyCost() {
        return hourlyCost;
    }

    public void setHourlyCost(double hourlyCost) {
        this.hourlyCost = hourlyCost;
    }

    public double getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(double workingHours) {
        this.workingHours = workingHours;
    }

    public double getWorkerProductivity() {
        return workerProductivity;
    }

    public void setWorkerProductivity(double workerProductivity) {
        this.workerProductivity = workerProductivity;
    }

    @Override
    public String toString() {
        return "WorkForce{" +
                "hourlyCost=" + hourlyCost +
                ", workingHours=" + workingHours +
                ", workerProductivity=" + workerProductivity +
                '}';
    }
}
