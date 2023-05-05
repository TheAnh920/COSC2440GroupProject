package vn.edu.rmit.groupprj;

/**
 * @author Group 21
 */
//add tax
 public class DigitalProduct extends Product {
    public DigitalProduct(String name, String desc, int quantity, double price, String taxType, double taxRate) {
        super(name, desc, quantity, price, taxType, taxRate);
    }

    @Override
    public String getType() {
        return "DIGITAL";
    }
}

