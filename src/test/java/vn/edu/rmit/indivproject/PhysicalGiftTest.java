package vn.edu.rmit.indivproject;

/**
 * @author Nguyen The Anh - s3927195
 */

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PhysicalGiftTest {
    @Test
    public void getsetMessageTest() {
        PhysicalGift pg = new PhysicalGift("Keyboard", "Custom mechanical", 100, 3000000, 3);
        pg.setMessage("clack clack");
        assertEquals("clack clack", pg.getMessage());
    }
}
