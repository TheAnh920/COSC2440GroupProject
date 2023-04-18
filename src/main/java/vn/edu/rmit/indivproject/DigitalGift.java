package vn.edu.rmit.indivproject;

/**
 * @author Nguyen The Anh - s3927195
 */

public class DigitalGift extends DigitalProduct implements CanBeGifted {
    private String message;

    public DigitalGift(String name, String desc, int quantity, double price) {
        super(name, desc, quantity, price);
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
