package vn.edu.rmit.groupprj;

/**
 * @author Group 21
 */
//add tax
public class PhysicalProduct extends Product {
    private double pWeight;

    public PhysicalProduct(String pName, String pDesc, int pQuantity, double pPrice, double pWeight, String taxType) {
        super(pName, pDesc, pQuantity, pPrice, taxType);
        this.pWeight = pWeight;
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


