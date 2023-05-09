package vn.edu.rmit.groupprj;

/**
 * @author Group 21
 */

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DigitalGiftTest {
    @Test
    public void getsetMessageTest() {
        DigitalGift dg = new DigitalGift("Fortnite battle pass", "Many good skins", 95, 185000, "normal tax");
        dg.setMessage("Let's play Fortnite!");
        assertEquals("Let's play Fortnite!", dg.getMessage());
    }
}
