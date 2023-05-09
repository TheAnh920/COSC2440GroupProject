package vn.edu.rmit.groupprj;

/**
 * @author Group 21
 */

import java.util.Map;
import java.util.Scanner;


public class Main {
    //  Declare a static variable activeCart to indicate the cart the user is interacting with
    static int activeCart;

    public static void main(String[] args) {
        System.out.println("COSC2440 GROUP PROJECT\n" +
                "Instructor: Mr. Tri Dang\n" +
                "Group: Group 21\n" +
                "s3927195 - Nguyen The Anh\n" +
                "s3926080 - Nguyen Bao Minh\n" +
                "s3928141 - Tran Viet Hoang\n" +
                "s3926234 - Nguyen Cong Chinh");
        // test
        Product.loadProducts();
        // end test

//      Create a default cart for users first opening the program
        ShoppingCart.cartList.put(1, new ShoppingCart());
//      Set the active cart to the newly created cart
        Main.activeCart = 1;
        Scanner scanner = new Scanner(System.in);

        Product.generateProducts();
        CouponController.generateCoupons();

//      Looping the menu for users to interact
        while (true) {
            System.out.println("=============================\n" +
                    "1. Display all products\n" +
                    "2. Search for products\n" +
                    "3. View detailed product\n" +
                    "4. Add product to cart\n" +
                    "5. Remove product from cart\n" +
                    "6. Display cart total price\n" +
                    "7. Create a new shopping cart\n" +
                    "8. Change cart\n" +
                    "9. Display all carts\n" +
                    "10. Create new product\n" +
                    "11. Edit a product\n" +
                    "12. View current cart\n" +
                    "13. Test\n" +
                    "14. Cart's tax\n" +
                    "15. Cart's receipt\n" +
                    "0. Exit");
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
                    String quantityStr;
//                  Input validation
                    while (true) {
                        quantityStr = scanner.nextLine();
//                      Only digits allowed and must be greater than 0
                        if (quantityStr.matches("^\\d+$") && !quantityStr.equals("0")) {
                            break;
                        }
                        System.out.print("That is not a valid quantity. Please try again: ");
                    }
                    if (ShoppingCart.cartList.get(activeCart).addItem(productName, Integer.parseInt(quantityStr))) {
                        System.out.println("Product added to cart successfully!");
                    } else {
                        System.out.println("Action cancelled.");
                    }
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
                    String keyStr;
//                  Input validation
                    while (true) {
                        keyStr = scanner.nextLine();
                        if (keyStr.matches("^\\d+$")) {
                            break;
                        }
                        System.out.print("That is not a valid key. Please try again: ");
                    }
                    int key = Integer.parseInt(keyStr);
                    ShoppingCart.changeCart(key);
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
                case "12":
                    System.out.println("Cart " + ShoppingCart.cartList.get(activeCart).getKey() + ":");
                    for (Map.Entry<String, Integer> pairEntry : ShoppingCart.cartList.get(activeCart).cart.entrySet()) {
                        System.out.println(pairEntry.getValue() + " x " + pairEntry.getKey());
                    }
                    break;
                case "13":
                    CouponController.allAvailableCoupon(activeCart);
                    System.out.print("Select the coupon you wish to use: ");
                    String choiceStr;
                    while (true) {
                        choiceStr = scanner.nextLine();
                        if (choiceStr.matches("^\\d+$")) {
                            break;
                        }
                        System.out.print("That is not a valid option. Please try again: ");
                    }
                    int choiceCoupon = Integer.parseInt(choiceStr);
                    CouponController.calcAmountOff(CouponController.applyCoupon(choiceCoupon - 1), choiceCoupon - 1);
                    break;
                case "14":
                    System.out.println("The total tax of cart " + ShoppingCart.cartList.get(activeCart).getKey() +
                            " is: " + ShoppingCart.cartList.get(activeCart).cartTax());
                    break;
                case "15":
                    ShoppingCart.cartList.get(activeCart).cartReceipt();
                    break;
                case "16":

                    break;
                default:
                    System.out.println("Invalid command.");
            }
        }
    }
}
