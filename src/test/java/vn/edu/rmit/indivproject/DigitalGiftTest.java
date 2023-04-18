package vn.edu.rmit.indivproject;

/**
 * @author Nguyen The Anh - s3927195
 */

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DigitalGiftTest {
    @Test
    public void getsetMessageTest() {
        DigitalGift dg = new DigitalGift("Fortnite battle pass", "Many good skins", 95, 185000);
        dg.setMessage("Let's play Fortnite!");
        assertEquals("Let's play Fortnite!", dg.getMessage());
    }
}
