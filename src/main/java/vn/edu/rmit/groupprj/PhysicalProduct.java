package vn.edu.rmit.groupprj;

/**
 * @author Group 21
 */
//add tax
 public class PhysicalProduct extends Product {
    private double pWeight;

    public PhysicalProduct(String name, String desc, int quantity, double price, double weight, String tType, double tRate) {
        super(name, desc, quantity, price, tType, tRate);
        pWeight = weight;
    }

    public double getpWeight() {
        return pWeight;
    }

    public void setpWeight(double weight) {
        pWeight = weight;
    }

    @Override
    public String getType() {
        return "PHYSICAL";
    }
}


