package vn.edu.rmit.groupprj;

/**
 * @author Group 21
 */

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
// test
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.stream.Stream;
// end test

public abstract class Product {
    static Map<String, Product> catalogue = new HashMap<>();
    private final String pName;
    private String pDesc;
    private int pQuantity;
    private double pPrice;

    public Product(String name, String desc, int quantity, double price) {
        pName = name;
        pDesc = desc;
        pQuantity = quantity;
        pPrice = price;
    }

    public String getpName() {
        return pName;
    }

    public String getpDesc() {
        return pDesc;
    }

    public int getpQuantity() {
        return pQuantity;
    }

    public double getpPrice() {
        return pPrice;
    }

    public void setpDesc(String desc) {
        pDesc = desc;
    }

    public void setpQuantity(int quantity) {
        pQuantity = quantity;
    }

    public void setpPrice(double price) {
        pPrice = price;
    }

    public abstract String getType();

    @Override
    public String toString() {
        return getType() + " - " + pName;
    }

    public static void createNewProduct() {
        Scanner scanner = new Scanner(System.in);
//      Ask user to choose a product type
        System.out.print("Choose product type (Digital/Physical): ");
        String type;
        while (true) {
            type = scanner.nextLine();
            if (type.equalsIgnoreCase("Digital") || type.equalsIgnoreCase("Physical")) {
                break;
            }
            System.out.print("That is not a valid product type. Please try again: ");
        }
//      Ask user to input the product's name
        System.out.print("Input product name: ");
        String name;
        while (true) {
            name = scanner.nextLine();
//          Check for name duplicates
            boolean name_exists = catalogue.get(name) != null;
            if (!name_exists) {
                break;
            }
            System.out.print("A product with that name already exists. Please try again: ");
        }
//      Ask user to input the product's descriptions
        System.out.print("Input product descriptions: ");
        String desc = scanner.nextLine();
//      Ask user to input the product's available quantity
        System.out.print("Input product quantity: ");
        int quantity;
        while (true) {
            quantity = scanner.nextInt();
            if (quantity >= 0) {
                break;
            }
            System.out.print("That is not a valid quantity. Please try again: ");
        }
//      Ask user to input the product's price
        System.out.print("Input product price: ");
        double price;
        while (true) {
            price = scanner.nextDouble();
            if (price >= 0) {
                break;
            }
            System.out.print("That is not a valid price. Please try again: ");
        }
//      If the product is a physical product, ask user to input its weight
        double weight = 0;
        if (type.equalsIgnoreCase("Physical")) {
            System.out.print("Input product weight: ");
            while (true) {
                weight = scanner.nextDouble();
                if (weight >= 0) {
                    break;
                }
                System.out.print("That is not a valid weight. Please try again: ");
            }
        }
//      Ask user if the product can be used as a gift
        System.out.println("Can this product be used as a gift? (Y/N)");
        String isGift;
        scanner.nextLine();
        while (true) {
            isGift = scanner.nextLine();
            if (isGift.equalsIgnoreCase("Y") || isGift.equalsIgnoreCase("N")) {
                break;
            }
            System.out.print("That is not a valid option. PLease try again: ");
        }
//      Put the new product into the catalogue
        if (type.equalsIgnoreCase("Digital") && isGift.equalsIgnoreCase("N")) {
            DigitalProduct dp = new DigitalProduct(name, desc, quantity, price);
            catalogue.put(name, dp);
        } else if (type.equalsIgnoreCase("Digital")) {
            DigitalGift dg = new DigitalGift(name, desc, quantity, price);
            catalogue.put(name, dg);
        } else if (isGift.equalsIgnoreCase("N")) {
            PhysicalProduct pp = new PhysicalProduct(name, desc, quantity, price, weight);
            catalogue.put(name, pp);
        } else {
            PhysicalGift pg = new PhysicalGift(name, desc, quantity, price, weight);
            catalogue.put(name, pg);
        }
        System.out.println("New product successfully added!");
    }

    public static void editProduct() {
        Scanner scanner = new Scanner(System.in);
//      Ask user to input the name of the product to be edited
        System.out.print("Input product name: ");
        String name = scanner.nextLine();
        if (!catalogue.containsKey(name)) {
            System.out.println("No product with that name found.");
        } else {
            System.out.println("Product found: " + catalogue.get(name).toString());
//          Edit the product's wanted attribute
            System.out.println("What information do you want to edit? (Descriptions/Quantity/Price/Weight)");
            String field = scanner.nextLine().toLowerCase();
            switch (field) {
                case "name":
                    System.out.println("Cannot change product name.");
                    break;
                case "descriptions":
                    System.out.print("Input new descriptions: ");
                    catalogue.get(name).setpDesc(scanner.nextLine());
                    System.out.println("Product descriptions edited successfully!");
                    break;
                case "quantity":
                    System.out.print("Input new quantity: ");
                    int quantity = scanner.nextInt();
                    if (quantity >= 0) {
                        catalogue.get(name).setpQuantity(quantity);
                        System.out.println("Product quantity edited successfully!");
                    } else {
                        System.out.println("Invalid quantity.");
                    }
                    break;
                case "price":
                    System.out.print("Input new price: ");
                    double price = scanner.nextDouble();
                    if (price >= 0) {
                        catalogue.get(name).setpPrice(price);
                        System.out.println("Product price edited successfully!");
                    } else {
                        System.out.println("Invalid price.");
                    }
                    break;
                case "weight":
                    if (catalogue.get(name).getType().equals("DIGITAL")) {
                        System.out.println("This is a digital product.");
                    } else if (catalogue.get(name).getType().equals("PHYSICAL")) {
                        System.out.print("Input new weight: ");
                        double weight = scanner.nextDouble();
                        if (weight >= 0) {
                            ((PhysicalProduct) catalogue.get(name)).setpWeight(weight);
                            System.out.println("Product weight edited successfully!");
                        } else {
                            System.out.println("Invalid weight.");
                        }
                    }
                    break;
            }
        }
    }

    public static boolean displayAllProducts() {
//      Loop through the HashMap and print out all products
        for (String productName : catalogue.keySet()) {
            System.out.println(catalogue.get(productName).toString());
        }
        return true;
    }

    public static boolean searchForProduct(String name) {
        boolean found = false;
        for (String productName : catalogue.keySet()) {
//          If there are products with their names containing the given name, print them out
            if (productName.contains(name)) {
                System.out.println(catalogue.get(productName).toString());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No products found.");
        }
        return found;
    }

    public static boolean viewDetailedProduct(String name) {
        if (!catalogue.containsKey(name)) {
            System.out.println("No products found.");
            return false;
        } else {
            if (catalogue.get(name).getType().equals("DIGITAL")) {
                System.out.println("Type: Digital product");
            }
            if (catalogue.get(name).getType().equals("PHYSICAL")) {
                System.out.println("Type: Physical product");
            }
            System.out.println("Name: " + catalogue.get(name).getpName());
            System.out.println("Descriptions: " + catalogue.get(name).getpDesc());
            System.out.println("Quantity: " + catalogue.get(name).getpQuantity());
            System.out.println("Price: " + catalogue.get(name).getpPrice());
            if (catalogue.get(name).getType().equals("PHYSICAL")) {
                System.out.println("Weight: " + ((PhysicalProduct) catalogue.get(name)).getpWeight());
            }
            if (catalogue.get(name) instanceof CanBeGifted) {
                System.out.println("This product can also be purchased as a gift!");
            }
            return true;
        }
    }
<<<<<<< Updated upstream
=======

    public static void generateProducts() {
        Product.catalogue.put("album", new DigitalProduct("album", "An album by Tyler the Creator",
                100, 10, "luxury tax"));
        Product.catalogue.put("towel", new PhysicalProduct("towel", "A towel for your home", 100,
                50, 0.7, "normal tax"));
        Product.catalogue.put("game", new DigitalGift("game", "Far Cry, an open-world FPS game", 100,
                50, "normal tax"));
        Product.catalogue.put("flower", new PhysicalGift("flower", "A bouquet of black-jack flowers, also " +
                "known as pig shit", 100, 10, 1, "tax-free"));
    }

    // test
    public static void loadProducts() {
        Path file = Paths.get("products.txt");
        try (Stream<String> stream = Files.lines(file)) {
            stream.forEach(line -> {
                String[] parts = line.split("\\|");
                String name = parts[0];
                String description = parts[1];
                int quantity = Integer.parseInt(parts[2]);
                double price = Double.parseDouble(parts[3]);
                double weight = Double.parseDouble(parts[4]);
                String taxType = parts[5];
                if (weight == 0) {
                    DigitalProduct dp = new DigitalProduct(name, description, quantity, price, taxType);
                    catalogue.put(name, dp);
                } else {
                    PhysicalProduct pp = new PhysicalProduct(name, description, quantity, price, weight, taxType);
                    catalogue.put(name, pp);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // end test
>>>>>>> Stashed changes
}
