/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emplcontrol.service;

import javax.inject.Inject;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author admin
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/ApplicationTest-context.xml"})
@Transactional
public class EnterDbTest {
    
    @Inject
    private EnterDb enterDb;
    @Inject
    private EmployeeService emplService;
    public EnterDbTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }
 
    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
  
    @After
    public void tearDown() {
    }

    /**
     * Test of randomFirstName method, of class EnterDb.
     */
    @Test
    public void testRandomFirstName() {
        assertNotNull("Not null", enterDb.randomFirstName());
    
    }

    /**
     * Test of randomLastName method, of class EnterDb.
     */
    @Test
    public void testRandomLastName() {
         assertNotNull("Not null", enterDb.randomLastName());
    }

    /**
     * Test of genDate method, of class EnterDb.
     */
    @Test
    public void testGenDate() throws Exception {
        assertNotNull("Not null", enterDb.genDate());
    }

    /**
     * Test of createEmpl method, of class EnterDb.
     */
    @Test
    public void testRandomDivisionId(){
          assertNotNull(enterDb.randomDivisionId());
    }
    
    @Test 
    public void testCreateDb() throws Exception {
        
        assertTrue(enterDb.createDb(10));
    }
}
