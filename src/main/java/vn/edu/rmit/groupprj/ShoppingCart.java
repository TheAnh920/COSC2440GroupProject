package vn.edu.rmit.groupprj;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ShoppingCart {
    static Map<Integer, ShoppingCart> cartList = new LinkedHashMap<>();
    //  Static int to count how many carts have been created
    static int count = 0;
    //  Set a unique key for each cart
    private final Integer key;
    Map<String, Integer> cart = new HashMap<>();
    ArrayList<String[]> messageList = new ArrayList<>();
    ArrayList<String[]> matchedList = new ArrayList<>();
    private double weight = 0;

    public ShoppingCart() {
//      Count how many carts have been created, then create the corresponding key, making the key unique
        count++;
        key = count;
    }

    public Integer getKey() {
        return key;
    }

    public double getWeight() {
        return weight;
    }

    public boolean addItem(String name, int quantity) {
//      Check if the product is present in the catalogue
        if (!Product.catalogue.containsKey(name)) {
            System.out.print("Product does not exist. ");
            return false;
        }
//      Check if the product is still adequately available
        if (Product.catalogue.get(name).getpQuantity() < quantity) {
            System.out.print("Not enough available in stock. ");
            return false;
        }
//      Begin adding product to cart
//      Decrease product quantity in stock
        Product.catalogue.get(name).setpQuantity(Product.catalogue.get(name).getpQuantity() - quantity);
//      If the added product is not already in cart, add it to the cart
        if (!cart.containsKey(name)) {
            cart.put(name, quantity);
//      If the added product is already in cart, increase its quantity in cart
        } else {
            cart.put(name, cart.get(name) + quantity);
        }
//      Refresh the cart total weight
        weight = 0;
        for (Map.Entry<String, Integer> pairEntry : cart.entrySet()) {
            if (Product.catalogue.get(pairEntry.getKey()).getType().equals("PHYSICAL")) {
                weight += ((PhysicalProduct) Product.catalogue.get(pairEntry.getKey())).getpWeight() * pairEntry.getValue();
            }
        }
        return true;
    }

    public ArrayList<String[]> appendMessage(String name, String message) {
        messageList.add(new String[]{name, message});
        return messageList;
    }

    public ArrayList<String[]> retrieveMatchedPairs(String matchName) {
        matchedList.clear();
        for (int i = 0; i < ShoppingCart.cartList.get(Main.activeCart).messageList.size(); i++)
            if (matchName.equalsIgnoreCase(ShoppingCart.cartList.get(Main.activeCart).messageList.get(i)[0])) {
                matchedList.add(new String[]{ShoppingCart.cartList.get(Main.activeCart).messageList.get(i)[0],
                        ShoppingCart.cartList.get(Main.activeCart).messageList.get(i)[1], String.valueOf(i)});
            }
        return matchedList;
    }

    public boolean printAllMessagePairs(){
        int count = 0;
        for (String[] strings : matchedList) {
            count++;
            System.out.println(count + ". " + strings[0] + " : " + strings[1]);
        }
        return true;
    }

    public boolean messageEdit(String changeChoice, String messageChange) {
        int index = Integer.parseInt(matchedList.get(Integer.parseInt(changeChoice) - 1)[2]);
        messageList.get(index)[1] = messageChange;
        return true;
    }

    public boolean removeItem(String name) {
        if (!cart.containsKey(name)) {
            return false;
        }
        Product.catalogue.get(name).setpQuantity(Product.catalogue.get(name).getpQuantity() + 1);
        cart.remove(name);
//      If the removed product is a physical product, deduct its weight from the total weight of the cart
        if (Product.catalogue.get(name).getType().equals("PHYSICAL")) {
            weight -= ((PhysicalProduct) Product.catalogue.get(name)).getpWeight();
        }
        return true;
    }

    public double cartAmount() {
        double price = 0;
        for (Map.Entry<String, Integer> pairEntry : cart.entrySet()) {
            price += (Product.catalogue.get(pairEntry.getKey()).getpPrice() * cart.get(pairEntry.getKey()));
        }
        return Math.round((price + weight * 0.1) * 100) / 100d;
    }

    public double cartTax() {
        double tax = 0;
        for (Map.Entry<String, Integer> pairEntry : cart.entrySet()) {
            if (Product.catalogue.get(pairEntry.getKey()).getpTaxType().equalsIgnoreCase("Luxury tax")) {
                tax += (Product.catalogue.get(pairEntry.getKey()).getpPrice() * cart.get(pairEntry.getKey()) * 0.2);
            } else if (Product.catalogue.get(pairEntry.getKey()).getpTaxType().equalsIgnoreCase("Normal tax")) {
                tax += (Product.catalogue.get(pairEntry.getKey()).getpPrice() * cart.get(pairEntry.getKey()) * 0.1);
            } else if (Product.catalogue.get(pairEntry.getKey()).getpTaxType().equalsIgnoreCase("Tax-free")) {
                tax += (Product.catalogue.get(pairEntry.getKey()).getpPrice() * cart.get(pairEntry.getKey()) * 0);
            }
        }
        return tax;
    }

    public static void createNewCart() {
        ShoppingCart newCart = new ShoppingCart();
        cartList.put(newCart.key, newCart);
//      Change the active cart to the newly created cart
        Main.activeCart = newCart.key;
        System.out.println("New shopping cart successfully created! Cart key: " + newCart.key);
    }

    public static void changeCart(int key) {
        if (!cartList.containsKey(key)) {
            System.out.println("Cart not found.");
        } else {
//          Change the active cart to the cart with the given key
            Main.activeCart = key;
            System.out.println("Changed to cart " + key + "!");
        }
    }

    public static boolean displayAllCarts() {
//      Transfer all carts' info to an ArrayList
        ArrayList<ShoppingCart> tempCartList = new ArrayList<>();
        for (Integer i : cartList.keySet()) {
            tempCartList.add(cartList.get(i));
        }
//      Sort the ArrayList based on the carts' weight
        tempCartList.sort(Comparator.comparingDouble(ShoppingCart::getWeight));
//      Clear the current cartList LinkedHashMap and reinsert the newly sorted ArrayList into the LinkedHashMap
        cartList.clear();
        for (ShoppingCart c : tempCartList) {
            cartList.put(c.key, c);
        }
//      Loop through the LinkedHashMap and print all carts
        for (Integer i : cartList.keySet()) {
            System.out.println("Cart " + cartList.get(i).key + " - weight: " + cartList.get(i).weight);
//            System.out.println(cartList.get(i).cart);
            for (Map.Entry<String, Integer> pairEntry : cartList.get(i).cart.entrySet()) {
                System.out.println(pairEntry.getValue() + " x " + pairEntry.getKey());
            }
        }
        return true;
    }

    public boolean cartReceipt() {
        try {
            String receiptName = "Cart " + key + " receipt.txt";
            FileWriter writer = new FileWriter(receiptName);
            System.out.println("FileWriter object created successfully.");
            writer.write("Cart " + key + " - weight: " + Math.round(weight * 100) / 100d + "\n\n");
            for (Map.Entry<String, Integer> pairEntry : cart.entrySet()) {
                writer.write(pairEntry.getKey() + " x " + pairEntry.getValue() + "   :   " + (Product.catalogue.get(pairEntry.getKey()).getpPrice() * pairEntry.getValue()) + "\n");
            }

            writer.write("Total amount (including shipping fee): " + cartAmount() + "\n");
            writer.write("Total tax: " + cartTax() + "\n");
            if (CouponController.isCouponAdded()) {
                writer.write("Coupon: " + Main.amountOff);
            }
            writer.write("\nTotal amount after tax: " + (cartAmount() + cartTax()) + "\n");
            writer.write("Date of purchase: " + new Date() + "\n");
            writer.close();
            System.out.println("Receipt saved to Cart " + key + " receipt.txt");
            return true;
        } catch (IOException e) {
            System.out.println("Error while writing receipt to file: " + e.getMessage());
            return false;
        }
    }
}
