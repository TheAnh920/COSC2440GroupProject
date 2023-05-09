package vn.edu.rmit.groupprj;

/**
 * @author Group 21
 */
//add tax
 public class PhysicalProduct extends Product {
    private double pWeight;

    
    public PhysicalProduct(String pName, String pDesc, int pQuantity, double pPrice, String tType, double pWeight) {
        super(pName, pDesc, pQuantity, pPrice, tType);
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


