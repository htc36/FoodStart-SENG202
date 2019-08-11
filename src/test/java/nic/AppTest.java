package nic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void testNothing() {
        assertEquals("This does basically nothing", "This does basically nothing");
    }
    
    /**
     * Tests if Alex is the one who wrote this test
     */
    
    @Test
    public void testWhoWroteThisTest() {
    	String whoWroteThisTest = "Alex";
    	
    	assertEquals(whoWroteThisTest, "Alex");
    }
}
