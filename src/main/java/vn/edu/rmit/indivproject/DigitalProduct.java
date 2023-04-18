package vn.edu.rmit.indivproject;

/**
 * @author Nguyen The Anh - s3927195
 */

public class DigitalProduct extends Product {
    public DigitalProduct(String name, String desc, int quantity, double price) {
        super(name, desc, quantity, price);
    }

    @Override
    public String getType() {
        return "DIGITAL";
    }
}
