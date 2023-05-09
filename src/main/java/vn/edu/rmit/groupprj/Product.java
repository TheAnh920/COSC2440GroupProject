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
    private String pTaxType;

    public Product(String pName, String pDesc, int pQuantity, double pPrice, String pTaxType) {
        this.pName = pName;
        this.pDesc = pDesc;
        this.pQuantity = pQuantity;
        this.pPrice = pPrice;
        this.pTaxType = pTaxType;
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

    public String getpTaxType() {
        return pTaxType;
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

    public void setpTaxType(String taxType) {
        pTaxType = taxType;
    }

    public abstract String getType();

    @Override
    public String toString() {
        return getType() + " - " + pName;
    }

    public static void createNewProduct() {
        Scanner scanner = new Scanner(System.in);
//      Ask user to choose a product type
        System.out.println("Choose product type:\n" +
                "1 - Digital\n" +
                "2 - Physical");
        String type;
//      Input validation
        while (true) {
            type = scanner.nextLine();
            if (type.equals("1") || type.equals("2")) {
                break;
            }
            System.out.print("That is not a valid option. Please try again: ");
        }
//      Ask user to input the product's name
        System.out.print("Input product name: ");
        String name;
//      Input validation
        while (true) {
            name = scanner.nextLine();
//          Check for name duplicates
            boolean name_exists = catalogue.get(name) != null;
            if (!name_exists && !name.contains("|") && !name.contains("\n")) {
                break;
            }
            System.out.print("Invalid name. Please try again: ");
        }
//      Ask user to input the product's descriptions
        System.out.print("Input product descriptions: ");
        String desc;
//      Input validation
        while (true) {
            desc = scanner.nextLine();
            if (!desc.contains("|") && !desc.contains("\n")) {
                break;
            }
            System.out.print("Descriptions contains invalid characters. Please try again: ");
        }
//      Ask user to input the product's available quantity
        System.out.print("Input product quantity: ");
        String quantityStr;
//      Input validation
        while (true) {
            quantityStr = scanner.nextLine();
            if (quantityStr.matches("^\\d+$")) {
                break;
            }
            System.out.print("That is not a valid quantity. Please try again: ");
        }
        int quantity = Integer.parseInt(quantityStr);
//      Ask user to input the product's price
        System.out.print("Input product price: ");
        String priceStr;
//      Input validation
        while (true) {
            priceStr = scanner.nextLine();
            if (priceStr.matches("\\d+(\\.\\d+)?")) {
                break;
            }
            System.out.print("That is not a valid price. Please try again: ");
        }
        double price = Math.round(Double.parseDouble(priceStr) * 100) / 100d;
//      If the product is a physical product, ask user to input its weight
        double weight = 0;
        if (type.equals("2")) {
            System.out.print("Input product weight: ");
            String weightStr;
            while (true) {
                weightStr = scanner.nextLine();
                if (weightStr.matches("\\d+(\\.\\d+)?")) {
                    break;
                }
                System.out.print("That is not a valid weight. Please try again: ");
            }
            weight = Math.round(Double.parseDouble(weightStr) * 100) / 100d;
        }

//      Ask user to choose a tax type
        System.out.println("Select tax type: ");
        String taxType;
        chooseTax:
        while (true) {
            System.out.println("1. Tax-free");
            System.out.println("2. Normal tax (10%)");
            System.out.println("3. Luxury tax (20%)");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    taxType = "Tax-free";
                    break chooseTax;
                case "2":
                    taxType = "Normal tax";
                    break chooseTax;
                case "3":
                    taxType = "Luxury tax";
                    break chooseTax;
                default:
                    System.out.print("That is not a valid option. Please try again: ");
            }
        }

//      Ask user if the product can be used as a gift
        System.out.println("Can this product be used as a gift? (Y/N)");
        String isGift;
        while (true) {
            isGift = scanner.nextLine();
            if (isGift.equalsIgnoreCase("Y") || isGift.equalsIgnoreCase("N")) {
                break;
            }
            System.out.print("That is not a valid option. PLease try again: ");
        }
//      Put the new product into the catalogue
        if (type.equals("1") && isGift.equalsIgnoreCase("N")) {
            DigitalProduct dp = new DigitalProduct(name, desc, quantity, price, taxType);
            catalogue.put(name, dp);
        } else if (type.equals("1")) {
            DigitalGift dg = new DigitalGift(name, desc, quantity, price, taxType);
            catalogue.put(name, dg);
        } else if (isGift.equalsIgnoreCase("N")) {
            PhysicalProduct pp = new PhysicalProduct(name, desc, quantity, price, weight, taxType);
            catalogue.put(name, pp);
        } else {
            PhysicalGift pg = new PhysicalGift(name, desc, quantity, price, weight, taxType);
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
            System.out.println("What information do you want to edit?\n" +
                    "1 - Descriptions\n" +
                    "2 - Quantity\n" +
                    "3 - Price\n" +
                    "4 - Weight");
            String field;
//          Input validation
            while (true) {
                field = scanner.nextLine();
                if (field.equals("1") || field.equals("2") || field.equals("3") || field.equals("4")) {
                    break;
                }
                System.out.print("That is not a valid option. Please try again: ");
            }
            switch (field) {
                case "1":
                    System.out.print("Input new descriptions: ");
                    String desc;
//                  Input validation
                    while (true) {
                        desc = scanner.nextLine();
                        if (!desc.contains("|") && !desc.contains("\n")) {
                            break;
                        }
                        System.out.print("Descriptions contains invalid characters. Please try again: ");
                    }
                    catalogue.get(name).setpDesc(desc);
                    System.out.println("Product descriptions edited successfully!");
                    break;
                case "2":
                    System.out.print("Input new quantity: ");
                    String quantityStr;
//                  Input validation
                    while (true) {
                        quantityStr = scanner.nextLine();
                        if (quantityStr.matches("^\\d+$")) {
                            break;
                        }
                        System.out.print("That is not a valid quantity. Please try again: ");
                    }
                    catalogue.get(name).setpQuantity(Integer.parseInt(quantityStr));
                    System.out.println("Product quantity updated successfully!");
                    break;
                case "3":
                    System.out.print("Input new price: ");
                    String priceStr;
//                  Input validation
                    while (true) {
                        priceStr = scanner.nextLine();
                        if (priceStr.matches("\\d+(\\.\\d+)?")) {
                            break;
                        }
                        System.out.print("That is not a valid price. Please try again: ");
                    }
                    catalogue.get(name).setpPrice(Math.round(Double.parseDouble(priceStr) * 100) / 100d);
                    System.out.println("Product price edited successfully!");
                    break;
                case "4":
                    if (catalogue.get(name).getType().equals("DIGITAL")) {
                        System.out.println("This is a digital product.");
                    } else if (catalogue.get(name).getType().equals("PHYSICAL")) {
                        System.out.print("Input new weight: ");
                        String weightStr;
                        while (true) {
                            weightStr = scanner.nextLine();
                            if (weightStr.matches("\\d+(\\.\\d+)?")) {
                                break;
                            }
                            System.out.print("That is not a valid weight. Please try again: ");
                        }
                        ((PhysicalProduct) catalogue.get(name)).setpWeight(Math.round(Double.parseDouble(weightStr) * 100) / 100d);
                        System.out.println("Product weight edited successfully!");
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
}
