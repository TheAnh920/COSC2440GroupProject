package vn.edu.rmit.indivproject;

/**
 * @author Nguyen The Anh - s3927195
 */

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DigitalProductTest {
    @Test
    public void toStringTest() {
        DigitalProduct dp = new DigitalProduct("Terraria", "Very good game", 920, 120000);
        assertEquals("DIGITAL - Terraria", dp.toString());
    }
}
