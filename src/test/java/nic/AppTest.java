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
    
    /**
     * A nonsense test written by Lydia
     */
    
    @Test
    public void testLydiaWroteThis() {
    	int testNum = 202;
    	
    	assertEquals(testNum, 202);
    }
    
    /**
     * Harrys tester case
     */
    
    @Test
    public void testHarryWroteThis() {
    	int tester = 584;
    	assertEquals(tester, 584);
    }
    
    /** 
     * Alan's test case
     */
    @Test
    public void alanTest() {
    	int i = 5;
    	assertTrue(i == 5);
    }
    
    /**
     * Frankie's tester case
     */
    
    @Test
    public void testForFrankie() {
    	int tester = 9876;
    	assertEquals(tester, 9876);
    }
    
}

