package vn.edu.rmit.groupprj;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ShoppingCart {
    static Map<Integer, ShoppingCart> cartList = new LinkedHashMap<>();
    //  Static int to count how many carts have been created
    static int count = 0;
    //  Unique key for each cart
    private final Integer key;
    Map<String, Integer> cart = new HashMap<>();
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
            if (Product.catalogue.get(pairEntry.getKey()).gettType() == "luxury tax") {
                tax += (Product.catalogue.get(pairEntry.getKey()).getpPrice() * cart.get(pairEntry.getKey()) * 0.2);
            } else if (Product.catalogue.get(pairEntry.getKey()).gettType() == "normal tax") {
                tax += (Product.catalogue.get(pairEntry.getKey()).getpPrice() * cart.get(pairEntry.getKey()) * 0.1);
            } else if (Product.catalogue.get(pairEntry.getKey()).gettType() == "Tax-free") {
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

    public void cartReceipt() {
        try {
            String receiptName = "Cart " + Main.activeCart + " receipt.txt";
            FileWriter writer = new FileWriter(receiptName);
            System.out.println("FileWriter object created successfully.");
            writer.write("Cart " + Main.activeCart + " - weight: " + cartList.get(Main.activeCart).weight + "\n");
            writer.write(cartList.get(Main.activeCart).cart + "\n");
            writer.write("Total amount: " + cartList.get(Main.activeCart).cartAmount() + "\n");
            writer.write("Total tax: " + cartList.get(Main.activeCart).cartTax() + "\n");
            writer.write("Total amount after tax: " + (cartList.get(Main.activeCart).cartAmount() + cartList.get(Main.activeCart).cartTax()) + "\n");
            writer.write("Date of purchase: " + new Date() + "\n");
            writer.close();
            System.out.println("Receipt saved to receipt.txt");
        } catch (IOException e) {
            System.out.println("Error while writing receipt to file: " + e.getMessage());
        }
    }
}
