package vn.edu.rmit.groupprj;

/**
 * @author Group 21
 */
//add tax
public class DigitalProduct extends Product {
    public DigitalProduct(String name, String desc, int quantity, double price, String taxType) {
        super(name, desc, quantity, price, taxType);
    }

    @Override
    public String getType() {
        return "DIGITAL";
    }
}

