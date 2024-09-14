package domain.entities;

public class Material extends Component {


    private double unitCoste;
    private double quantity;
    private double transportCost;
    private double coefficientQuality;


    public Material(String name, String componentType, double vatRate, double unitCoste, double quantity, double transportCost, double coefficientQuality) {
        super(name, componentType, vatRate);
        this.unitCoste = unitCoste;
        this.quantity = quantity;
        this.transportCost = transportCost;
        this.coefficientQuality = coefficientQuality;
    }

    public Material() {
    }

    public double getUnitCoste() {
        return unitCoste;
    }

    public void setUnitCoste(double unitCoste) {
        this.unitCoste = unitCoste;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getTransportCost() {
        return transportCost;
    }

    public void setTransportCost(double transportCost) {
        this.transportCost = transportCost;
    }

    public double getCoefficientQuality() {
        return coefficientQuality;
    }

    public void setCoefficientQuality(double coefficientQuality) {
        this.coefficientQuality = coefficientQuality;
    }

    @Override
    public String toString() {
        return "Material{" +
                "unitCoste=" + unitCoste +
                ", quantity=" + quantity +
                ", transportCost=" + transportCost +
                ", coefficientQuality=" + coefficientQuality +
                '}';
    }
}
