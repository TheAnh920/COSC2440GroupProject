package vn.edu.rmit.groupprj;

/**
 * @author Group 21
 */

public class PhysicalProduct extends Product {
    private double pWeight;

    public PhysicalProduct(String name, String desc, int quantity, double price, double weight) {
        super(name, desc, quantity, price);
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
