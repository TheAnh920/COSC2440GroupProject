package vn.edu.rmit.groupprj;

/**
 * @author Group 21
 */

import java.util.*;

public class ShoppingCart {
    static Map<Integer, ShoppingCart> cartList = new LinkedHashMap<>();
    //  Static int to count how many carts have been created
    static int count = 0;
    //  Unique key for each cart
    private final Integer key;
    Map<Product, Integer> cart = new HashMap<>();
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

    public boolean addItem(String productName, int productQuant) {
        if (!Product.catalogue.containsKey(productName)) {
            return false;
        }
        if (Product.catalogue.get(productName).getpQuantity() <= 0) {
            return false;
        }
        if (cart.containsKey(productName)) {
            return false;
        }
        Product.catalogue.get(productName).setpQuantity(Product.catalogue.get(productName).getpQuantity() - 1);
        cart.put(Product.catalogue.get(productName), productQuant);
//      If the added product is a physical product, add its weight to the total weight of the cart
        if (Product.catalogue.get(productName).getType().equals("PHYSICAL")) {
            weight += ((PhysicalProduct) Product.catalogue.get(productName)).getpWeight();
        }
        return true;
    }

    public boolean removeItem(String productName) {
        if (!cart.containsKey(productName)) {
            return false;
        }
        Product.catalogue.get(productName).setpQuantity(Product.catalogue.get(productName).getpQuantity() + 1);
        cart.remove(productName);
//      If the removed product is a physical product, deduct its weight from the total weight of the cart
        if (Product.catalogue.get(productName).getType().equals("PHYSICAL")) {
            weight -= ((PhysicalProduct) Product.catalogue.get(productName)).getpWeight();
        }
        return true;
    }

    public double cartAmount() {
        double price = 0;
        for (Map.Entry<Product, Integer> pairEntry: cart.entrySet()) {
            price += Product.catalogue.get(pairEntry.getKey().getpName()).getpPrice() * cart.get(pairEntry.getKey());
        }

        
        return Math.round((price + weight * 0.1) * 100) / 100d;
    }

    public static void createNewCart() {
        ShoppingCart newCart = new ShoppingCart();
        cartList.put(newCart.key, newCart);
//      Change the active cart to the newly created cart
        Main.activeCart = newCart.key;
        System.out.println("New shopping cart successfully created! Cart key: " + newCart.key);
    }

    public static void changeCart() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input cart key: ");
        int tempKey = scanner.nextInt();
        if (!cartList.containsKey(tempKey)) {
            System.out.println("Cart not found.");
        } else {
//          Change the active cart to the cart with the given key
            Main.activeCart = tempKey;
            System.out.println("Changed to cart " + tempKey + "!");
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
            System.out.println(cartList.get(i).cart);
        }
        return true;
    }
}
