package vn.edu.rmit.groupprj;

/**
 * @author Group 21
 */

import java.util.Map;
import java.util.Scanner;


public class Main {
    //  Declare a static variable activeCart to indicate the cart the user is interacting with
    static int activeCart;
    static double amountOff;


    public static void main(String[] args) {
        System.out.println("COSC2440 GROUP PROJECT\n" +
                "Instructor: Mr. Tri Dang\n" +
                "Group: Group 21\n" +
                "s3927195 - Nguyen The Anh\n" +
                "s3926080 - Nguyen Bao Minh\n" +
                "s3928141 - Tran Viet Hoang\n" +
                "s3926234 - Nguyen Cong Chinh");
        Product.loadProducts();
        CouponController.generateCoupons();
//      Create a default cart for users first opening the program
        ShoppingCart.cartList.put(1, new ShoppingCart());
//      Set the active cart to the newly created cart
        Main.activeCart = 1;
        Scanner scanner = new Scanner(System.in);
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
                    "12. Apply coupon to cart\n" +
                    "13. Cart's receipt\n" +
                    "14. View and Edit gift messages\n" +
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
                        if (Product.catalogue.get(productName) instanceof CanBeGifted) {
                            System.out.println("Product can be used as a gift. Would you like to add a message to all products?");
                            System.out.println("Y: Yes");
                            System.out.println("N: No");
                            String giftChoice = scanner.nextLine();
                            if (giftChoice.equalsIgnoreCase("y")) {
                                System.out.print("Enter your message: ");
                                String message = scanner.nextLine();
                                for (int i = 0; i < Integer.parseInt(quantityStr); i++) {
                                    ShoppingCart.cartList.get(activeCart).appendMessage(productName, message);
                                }

                            } else if (giftChoice.equalsIgnoreCase("n")) {
                                for (int i = 0; i < Integer.parseInt(quantityStr); i++){
                                    ShoppingCart.cartList.get(activeCart).appendMessage(productName, null);
                                }
                                
                            }
                        }
                        System.out.println("Product added to cart successfully!");
                    } else {
                        System.out.println("Action cancelled.");
                    }
                    break;
                case "5":
                System.out.print("Enter name of product you want to remove: ");
                String name = scanner.nextLine();
                ShoppingCart.cartList.get(activeCart).retrieveMatchedPairs(name);
                ShoppingCart.cartList.get(activeCart).printAllMessagePairs(name);
                System.out.print("Enter the number of the product you want to remove: ");
                String removeChoice = scanner.nextLine();
                ShoppingCart.cartList.get(activeCart).removeItemByChoice(removeChoice, name);
                System.out.println("Product removed from cart successfully!");
                
                break;

                
                case "6":
                    System.out.println("Cart " + ShoppingCart.cartList.get(activeCart).getKey() + ":");
                    for (Map.Entry<String, Integer> pairEntry : ShoppingCart.cartList.get(activeCart).cart.entrySet()) {
                        System.out.println(pairEntry.getValue() + " x " + pairEntry.getKey() + " : " + Product.catalogue.get(pairEntry.getKey()).getpPrice() * pairEntry.getValue());
                    }
                    System.out.println("\nTax: " + ShoppingCart.cartList.get(activeCart).cartTax());
                    if (CouponController.isCouponAdded()) {
                        System.out.println("Coupon: -" + amountOff);
                    }
                    System.out.println("Cart " + ShoppingCart.cartList.get(activeCart).getKey() +
                            " total: " + (ShoppingCart.cartList.get(activeCart).cartAmount() + ShoppingCart.cartList.get(activeCart).cartTax() - amountOff));
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
                    CouponController.setCouponAdded(false);
                    amountOff = 0;
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
                    amountOff = CouponController.calcAmountOff(CouponController.applyCoupon(choiceCoupon - 1), choiceCoupon - 1);
                    break;
                case "13":
                    ShoppingCart.cartList.get(activeCart).cartReceipt();
                    break;
                case "14":
                    System.out.print("Enter name of product you wish to view the message of: ");
                    String messageName = scanner.nextLine();
                    ShoppingCart.cartList.get(activeCart).retrieveMatchedPairs(messageName);
                    ShoppingCart.cartList.get(activeCart).printAllMessagePairs();
                    System.out.print("Enter the number of the message you wish to change: ");
                    String messageChoice;
                    while (true) {
                        messageChoice = scanner.nextLine();
                        if (messageChoice.matches("^\\d+$") && 0 < Integer.parseInt(messageChoice) && Integer.
                                parseInt(messageChoice) <= ShoppingCart.cartList.get(activeCart).matchedList.size()) {
                            break;
                        }
                        System.out.print("Invalid input. Please try again: ");
                    }
                    System.out.print("Enter its new message: ");
                    String messageChange = scanner.nextLine();
                    ShoppingCart.cartList.get(activeCart).messageEdit(messageChoice, messageChange);
                    System.out.println(ShoppingCart.cartList.get(activeCart).messageList.get(0)[1]);
                    break;
                default:
                    System.out.println("Invalid command.");
            }
        }
    }
}

