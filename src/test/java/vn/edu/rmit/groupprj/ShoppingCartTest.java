package vn.edu.rmit.groupprj;

/**
 * @author Group 21
 */

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ShoppingCartTest {
    @Test
    public void addItemTrueTest() {
        Product.catalogue.put("name", new DigitalProduct("name", "desc", 10, 10, "tax-free"));
        ShoppingCart sc = new ShoppingCart();
        ShoppingCart.cartList.put(sc.getKey(), sc);
        assertTrue(sc.addItem("name", 1));
    }

    @Test
    public void addItemTestNotFound() {
        Product.catalogue.put("name", new DigitalProduct("name", "desc", 10, 10, "luxury tax"));
        ShoppingCart sc = new ShoppingCart();
        ShoppingCart.cartList.put(sc.getKey(), sc);
        assertFalse(sc.addItem("no name", 1));
    }

    @Test
    public void addItemTestOutOfStock() {
        Product.catalogue.put("name", new DigitalProduct("name", "desc", 0, 10, "normal tax"));
        ShoppingCart sc = new ShoppingCart();
        ShoppingCart.cartList.put(sc.getKey(), sc);
        assertFalse(sc.addItem("name", 1));
    }

    @Test
    public void removeItemTrueTest() {
        Product.catalogue.put("name", new DigitalProduct("name", "desc", 10, 10, "normal tax"));
        ShoppingCart sc = new ShoppingCart();
        ShoppingCart.cartList.put(sc.getKey(), sc);
        sc.addItem("name", 1);
        assertTrue(sc.removeItem("name", 1));
    }

    @Test
    public void removeItemFalseTest() {
        ShoppingCart sc = new ShoppingCart();
        ShoppingCart.cartList.put(sc.getKey(), sc);
        assertFalse(sc.removeItem("name", 1));
    }

    @Test
    public void cartAmountTest() {
        Product.catalogue.put("name", new DigitalProduct("name", "desc", 10, 10, "luxury tax"));
        Product.catalogue.put("name2", new PhysicalProduct("name2", "desc", 10, 10, 10, "tax free"));
        ShoppingCart sc = new ShoppingCart();
        ShoppingCart.cartList.put(sc.getKey(), sc);
        sc.addItem("name", 1);
        sc.addItem("name2", 1);
        assertEquals(sc.cartAmount(), 21);
    }

    @Test
    public void displayAllCartsTest() {
        Product.catalogue.put("name", new DigitalProduct("name", "desc", 10, 10, "luxury tax"));
        Product.catalogue.put("name2", new PhysicalProduct("name2", "desc", 10, 10, 15, "normal tax"));
        Product.catalogue.put("name3", new PhysicalProduct("name3", "desc", 10, 10, 10, "tax-free"));
        ShoppingCart sc = new ShoppingCart();
        ShoppingCart.cartList.put(sc.getKey(), sc);
        sc.addItem("name2", 1);
        ShoppingCart sc2 = new ShoppingCart();
        ShoppingCart.cartList.put(sc2.getKey(), sc2);
        sc2.addItem("name2", 1);
        sc2.addItem("name3", 1);
        ShoppingCart sc3 = new ShoppingCart();
        ShoppingCart.cartList.put(sc3.getKey(), sc3);
        sc3.addItem("name3", 1);
        ShoppingCart sc4 = new ShoppingCart();
        ShoppingCart.cartList.put(sc4.getKey(), sc4);
        sc4.addItem("name", 1);
        assertTrue(ShoppingCart.displayAllCarts());
    }

    @Test
    public void cartReceiptTest() {
        ShoppingCart sc = new ShoppingCart();
        assertTrue(sc.cartReceipt());
    }

    @Test
    public void messageEditTest() {
        ShoppingCart sc = new ShoppingCart();
        sc.matchedList.add(new String[] {"snack", "hello", "0"});
        sc.messageList.add(new String [] {"snack", "hello"});

        assertTrue(sc.messageEdit("1", "hello"));
    }

    @Test
    public void printAllMessagePairsTest() {
        ShoppingCart sc = new ShoppingCart();
        sc.matchedList.add(new String[] {"snack", "hello", "0"});
        sc.messageList.add(new String [] {"snack", "hello"});

        assertTrue(sc.printAllMessagePairs());
    }


    @Test
    public void appendMessageTest() {
        ShoppingCart sc = new ShoppingCart();
        sc.appendMessage("snack", "hello");

        assertEquals(sc.messageList.get(0)[0], "snack");
        assertEquals(sc.messageList.get(0)[1], "hello");
    }

    @Test
    public void cartTaxTest() {
        
        Product.catalogue.put("album", new DigitalProduct("album", "An album by Tyler the Creator",
        100, 10, "Luxury tax"));
        Product.catalogue.put("towel", new PhysicalProduct("towel", "A towel for your home", 100,
        50, 0.7, "Normal tax"));

        ShoppingCart sc = new ShoppingCart();
        sc.cart.put("towel", 2);
        sc.cart.put("album", 2);

        assertEquals(sc.cartTax(), 14); 
    }
}