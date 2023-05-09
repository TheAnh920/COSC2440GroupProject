package vn.edu.rmit.groupprj;

/**
 * @author Group 21
 */

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductTest {
    @Test
    public void displayAllProductsTest() {
        Product.catalogue.put("name", new DigitalProduct("name", "desc", 10, 10, "tax-free"));
        Product.catalogue.put("name2", new PhysicalProduct("name2", "desc", 10, 10, 15, "luxury tax"));
        Product.catalogue.put("name3", new PhysicalProduct("name3", "desc", 10, 10, 10, "luxury tax"));
        Product.catalogue.put("name4", new DigitalGift("name4", "desc", 10, 10, "tax-free"));
        Product.catalogue.put("name5", new PhysicalGift("name5", "desc", 10, 10, 20, "normal tax"));
        assertTrue(Product.displayAllProducts());
    }

    @Test
    public void searchForProductTrueTest() {
        Product.catalogue.put("name", new DigitalProduct("name", "desc", 10, 10, "normal tax"));
        Product.catalogue.put("name12", new PhysicalProduct("name12", "desc", 10, 10, 15, "normal tax"));
        Product.catalogue.put("name13", new PhysicalProduct("name13", "desc", 10, 10, 10, "normal tax"));
        Product.catalogue.put("name4", new DigitalGift("name4", "desc", 10, 10, "tax-free"));
        Product.catalogue.put("name15", new PhysicalGift("name15", "desc", 10, 10, 20, "tax-free"));
        assertTrue(Product.searchForProduct("name1"));
    }

    @Test
    public void searchForProductFalseTest() {
        Product.catalogue.put("name", new DigitalProduct("name", "desc", 10, 10, "normal tax"));
        assertFalse(Product.searchForProduct("no name"));
    }

    @Test
    public void viewDetailedProductTrueTest() {
        Product.catalogue.put("name15", new PhysicalGift("name15", "desc", 10, 10, 20, "luxury tax"));
        assertTrue(Product.viewDetailedProduct("name15"));
    }

    @Test
    public void viewDetailedProductFalseTest() {
        Product.catalogue.put("name15", new PhysicalGift("name15", "desc", 10, 10, 20, "tax-free"));
        assertFalse(Product.viewDetailedProduct("no name"));
    }
}
