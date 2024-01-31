package uk.ac.aber.cs21120.knockout.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.ac.aber.cs21120.knockout.interfaces.IPlayer;
import uk.ac.aber.cs21120.knockout.solution.Player;

public class PlayerTests {
    /**
     * Test that trying to create a position=0 player fails. I don't really care what exception
     * it throws.
     */
    @Test
    void testCreateInvalidPositionZero(){
        Assertions.assertThrows(Exception.class, () -> {
            IPlayer p = new Player("Barry", 0);
        });
    }

    /**
     * Test that trying to create a position<0 player fails. I don't really care what exception
     * it throws.
     */
    @Test
    void testCreateInvalidPositionNegative(){
        Assertions.assertThrows(Exception.class, () -> {
            IPlayer p = new Player("Barry", -1);
        });
    }

    /**
     * Test we can get a player's name
     */
    @Test
    void testGetName(){
        IPlayer p = new Player("Dave", 2);
        Assertions.assertEquals("Dave", p.getName());
    }

    /**
     * Test we can get a player's number
     */
    @Test
    void testGetPosition(){
        IPlayer p = new Player("Keith", 3);
        Assertions.assertEquals(3, p.getPosition());
    }

    /**
     * Test that two players can have different names (that you haven't used "static!")
     */
    @Test
    void testPlayersDifferentNames() {
        IPlayer p1 = new Player("A", 1);
        IPlayer p2 = new Player("B", 2);
        Assertions.assertNotEquals(p1.getName(), p2.getName());
    }

    /**
     * Test that two players can have different positions (that you haven't used "static!")
     */
    @Test
    void testPlayersDifferentPositions() {
        IPlayer p1 = new Player("A", 1);
        IPlayer p2 = new Player("B", 2);
        Assertions.assertNotEquals(p1.getPosition(), p2.getPosition());
    }
}
