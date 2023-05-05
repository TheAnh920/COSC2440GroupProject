package vn.edu.rmit.groupprj;

/**
 * @author Group 21
 */
//add tax
 public class PhysicalGift extends PhysicalProduct implements CanBeGifted {
    private String message;

    public PhysicalGift(String name, String desc, int quantity, double price, double weight, String taxType, double taxRate) {
        super(name, desc, quantity, price, weight, taxType, taxRate);
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String msg) {
        message = msg;
    }
}
