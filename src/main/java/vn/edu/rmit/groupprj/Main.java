package vn.edu.rmit.groupprj;

/**
 * @author Group 21
 */

import java.util.Scanner;

public class Main {
    //  Declare a static variable activeCart to indicate the cart the user is interacting with
    static int activeCart;

    public static void main(String[] args) {
        System.out.println("COSC2440 INDIVIDUAL PROJECT\n" + "Instructor: Mr. Tri Dang\n" + "s3927195 - Nguyen The Anh");
//      Create a default cart for users first opening the program
        ShoppingCart.cartList.put(1, new ShoppingCart());
//      Set the active cart to the newly created cart
        Main.activeCart = 1;
        Scanner scanner = new Scanner(System.in);
//      Looping the menu for users to interact
        while (true) {
            System.out.println("=============================");
            System.out.println("1. Display all products");
            System.out.println("2. Search for products");
            System.out.println("3. View detailed product");
            System.out.println("4. Add product to cart");
            System.out.println("5. Remove product from cart");
            System.out.println("6. Display cart total price");
            System.out.println("7. Create a new shopping cart");
            System.out.println("8. Change cart");
            System.out.println("9. Display all carts");
            System.out.println("10. Create new product");
            System.out.println("11. Edit a product");
            System.out.println("0. Exit");
            String option = scanner.nextLine();
            if (option.equals("0")) {
                break;
            }
            switch (option) {
                case "1":
                    Product.displayAllProducts();
                    break;
                case "2":
                    System.out.print("Input product name: ");
                    Product.searchForProduct(scanner.nextLine());
                    break;
                case "3":
                    System.out.print("Input product name: ");
                    Product.viewDetailedProduct(scanner.nextLine());
                    break;
                case "4":
                    System.out.print("Input product name: ");
                    String productName = scanner.nextLine();
                    System.out.print("Input product quantity: ");
                    int productQuantity = scanner.nextInt();
                    if (ShoppingCart.cartList.get(activeCart).addItem(productName, productQuantity)) {
                        System.out.println("Product added to cart successfully!");
                    } else {
                        System.out.println("Failed, the selected product is out of stock or does not exist!");
                    }
                    scanner.nextLine();
                    break;
                case "5":
                    System.out.print("Input product name: ");
                    if (ShoppingCart.cartList.get(activeCart).removeItem(scanner.nextLine())) {
                        System.out.println("Product removed from cart successfully!");
                    } else {
                        System.out.println("Product not found in cart!");
                    }
                    break;
                case "6":
                    System.out.println("The total price of cart " + ShoppingCart.cartList.get(activeCart).getKey() +
                            " is: " + ShoppingCart.cartList.get(activeCart).cartAmount());
                    break;
                case "7":
                    ShoppingCart.createNewCart();
                    break;
                case "8":
                    System.out.print("Input cart key: ");
                    int tempKey = scanner.nextInt();
                    ShoppingCart.changeCart(tempKey);
                    break;
                case "9":
                    ShoppingCart.displayAllCarts();
                    break;
                case "10":
                    Product.createNewProduct();
                    break;
                case "11":
                    Product.editProduct();
                    break;
                default:
                    System.out.println("Invalid command.");
            }
        }
    }
}
