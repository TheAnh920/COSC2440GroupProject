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
                    "12. Test\n" +
                    "13. Cart's receipt\n" +
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
                    String detailedName = scanner.nextLine();
                    Product.viewDetailedProduct(detailedName);
                    if (CouponController.CouponCata.containsKey(detailedName)) {
                        System.out.println("Coupons: ");
                        for (int i = 0; i < CouponController.CouponCata.get(detailedName).size(); i++) {
                            System.out.println(CouponController.CouponCata.get(detailedName).get(i).getCouponDesc());
                        }
                    }
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
                    System.out.println("Cart " + ShoppingCart.cartList.get(activeCart).getKey() + ":");
                    for (Map.Entry<String, Integer> pairEntry : ShoppingCart.cartList.get(activeCart).cart.entrySet()) {
                        System.out.println(pairEntry.getValue() + " x " + pairEntry.getKey() + " : " + Product.catalogue.get(pairEntry.getKey()).getpPrice() * pairEntry.getValue());
                    }
                    System.out.println("\nTax: " + ShoppingCart.cartList.get(activeCart).cartTax());
                    if (CouponController.isCouponAdded()) {
                        System.out.println("Coupon: " + amountOff);
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
                default:
                    System.out.println("Invalid command.");
            }
        }
    }
}
