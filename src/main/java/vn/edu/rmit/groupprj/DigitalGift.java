package vn.edu.rmit.groupprj;

import java.util.ArrayList;

/**
 * @author Group 21
 */

public class DigitalGift extends DigitalProduct implements CanBeGifted {
    private String message;
    ArrayList<String[]> matchedList = new ArrayList<>();

    public DigitalGift(String name, String desc, int quantity, double price, String taxType) {
        super(name, desc, quantity, price, taxType);
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
