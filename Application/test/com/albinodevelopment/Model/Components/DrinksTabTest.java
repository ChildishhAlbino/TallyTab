/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinodevelopment.Model.Components;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author conno
 */
public class DrinksTabTest {
    
    public DrinksTabTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of CheckValues method, of class DrinksTab.
     */
    @Test
    public void testCheckValues() {
        System.out.println("CheckValues");
        DrinksTab instance = null;
        instance.CheckValues();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of changeDrinkAmount method, of class DrinksTab.
     */
    @Test
    public void testChangeDrinkAmount() {
        System.out.println("changeDrinkAmount");
        int delta = 0;
        Drink drink = null;
        DrinksTab instance = null;
        instance.changeDrinkAmount(delta, drink);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of GetCount method, of class DrinksTab.
     */
    @Test
    public void testGetCount() {
        System.out.println("GetCount");
        Drink drink = null;
        DrinksTab instance = null;
        int expResult = 0;
        int result = instance.GetCount(drink);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDrinkSubtotal method, of class DrinksTab.
     */
    @Test
    public void testGetDrinkSubtotal() {
        System.out.println("getDrinkSubtotal");
        Drink drink = null;
        DrinksTab instance = null;
        double expResult = 0.0;
        double result = instance.getDrinkSubtotal(drink);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of GetPercentUsed method, of class DrinksTab.
     */
    @Test
    public void testGetPercentUsed() {
        System.out.println("GetPercentUsed");
        DrinksTab instance = null;
        double expResult = 0.0;
        double result = instance.GetPercentUsed();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of GetCurrentValue method, of class DrinksTab.
     */
    @Test
    public void testGetCurrentValue() {
        System.out.println("GetCurrentValue");
        DrinksTab instance = null;
        double expResult = 0.0;
        double result = instance.GetCurrentValue();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of GetLimit method, of class DrinksTab.
     */
    @Test
    public void testGetLimit() {
        System.out.println("GetLimit");
        DrinksTab instance = null;
        double expResult = 0.0;
        double result = instance.GetLimit();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ChangeLimit method, of class DrinksTab.
     */
    @Test
    public void testChangeLimit() {
        System.out.println("ChangeLimit");
        double limit = 0.0;
        DrinksTab instance = null;
        instance.ChangeLimit(limit);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDrinksList method, of class DrinksTab.
     */
    @Test
    public void testGetDrinksList() {
        System.out.println("getDrinksList");
        DrinksTab instance = null;
        DrinksList expResult = null;
        DrinksList result = instance.getDrinksList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of init method, of class DrinksTab.
     */
    @Test
    public void testInit() {
        System.out.println("init");
        DrinksTab instance = null;
        instance.init();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDrinksTabItem method, of class DrinksTab.
     */
    @Test
    public void testGetDrinksTabItem() {
        System.out.println("getDrinksTabItem");
        Drink drink = null;
        DrinksTab instance = null;
        DrinksTabItem expResult = null;
        DrinksTabItem result = instance.getDrinksTabItem(drink);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of round method, of class DrinksTab.
     */
    @Test
    public void testRound() {
        System.out.println("round");
        double value = 0.0;
        int places = 0;
        double expResult = 0.0;
        double result = DrinksTab.round(value, places);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
