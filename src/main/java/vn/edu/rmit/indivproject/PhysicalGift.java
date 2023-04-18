package vn.edu.rmit.indivproject;

/**
 * @author Nguyen The Anh - s3927195
 */

public class PhysicalGift extends PhysicalProduct implements CanBeGifted {
    private String message;

    public PhysicalGift(String name, String desc, int quantity, double price, double weight) {
        super(name, desc, quantity, price, weight);
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
