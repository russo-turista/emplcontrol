package com.emplcontrol.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.emplcontrol.domain.Employees;



public class EmployeeTest {
	private Employees s;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		s = new Employees();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetSalary() {
		s.setSalary( (double) 1000);
		assertFalse("Is NAN?", s.getSalary().isNaN());
	}

	@Test
	public void testSetFirstName() {
		s.setFirstName("Proverka");
		assertEquals("Test for equals","Proverka", s.getFirstName());
	}
        @Test
	public void testSetLastName() {
		s.setLastName("Proverka2");
		assertEquals("Test for equals","Proverka2", s.getLastName());
	}

}
