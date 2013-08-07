package com.emplcontrol.service;

import static org.junit.Assert.assertEquals;
import org.junit.*;

public class StringFormTest {
	protected StringForm stringfrom;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		stringfrom = new StringForm();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetStringName() {
		String str1 = "sEa";
		String str2 = "Sea";
		assertEquals("Not eguals", str2,  stringfrom.getStringName(str1));
	}

}
