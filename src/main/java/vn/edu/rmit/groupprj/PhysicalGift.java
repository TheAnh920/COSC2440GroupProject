package vn.edu.rmit.groupprj;

import java.util.ArrayList;

/**
 * @author Group 21
 */
//add tax
public class PhysicalGift extends PhysicalProduct implements CanBeGifted {
    private String message;
    ArrayList<String[]> matchedList = new ArrayList<>();

    public PhysicalGift(String name, String desc, int quantity, double price, double weight, String taxType) {
        super(name, desc, quantity, price, weight, taxType);
    }

    @Override
    public String getMessage() {
        
        return message;
    }

    public ArrayList<String[]> retrieveMatchMessage(String matchName) {
        int count = 0;
        for (int i = 0; i < ShoppingCart.cartList.get(Main.activeCart).messageList.size(); i++)
            if (matchName.equalsIgnoreCase(ShoppingCart.cartList.get(Main.activeCart).messageList.get(i)[0])) {
                count++;
                System.out.println(count + ShoppingCart.cartList.get(Main.activeCart).messageList.get(i)[0] + ShoppingCart.cartList.get(Main.activeCart).messageList.get(i)[1]);
                matchedList.add(new String[] {ShoppingCart.cartList.get(Main.activeCart).messageList.get(i)[0], ShoppingCart.cartList.get(Main.activeCart).messageList.get(i)[1]});
            }
        return matchedList;
    }

    public void displayChoiceMessage(ArrayList<String[]> matchedList, int changeChoice) {
        
    }

    @Override
    public void setMessage(String msg) {
        message = msg;
    }
}
