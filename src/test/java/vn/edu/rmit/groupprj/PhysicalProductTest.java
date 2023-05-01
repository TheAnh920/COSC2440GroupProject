package vn.edu.rmit.groupprj;

/**
 * @author Group 21
 */

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PhysicalProductTest {
    @Test
    public void toStringTest() {
        PhysicalProduct pp = new PhysicalProduct("Mouse", "Very good mouse", 92, 1000000, 92);
        assertEquals("PHYSICAL - Mouse", pp.toString());
    }
}
