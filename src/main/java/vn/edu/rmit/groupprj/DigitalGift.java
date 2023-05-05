package vn.edu.rmit.groupprj;

/**
 * @author Group 21
 */

 public class DigitalGift extends DigitalProduct implements CanBeGifted {
    private String message;

    public DigitalGift(String name, String desc, int quantity, double price, String taxType, double taxRate) {
        super(name, desc, quantity, price, taxType, taxRate);
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
