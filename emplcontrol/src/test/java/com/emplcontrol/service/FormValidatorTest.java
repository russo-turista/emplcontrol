package com.emplcontrol.service;

import java.util.Calendar;
import java.util.GregorianCalendar;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.*;

public class FormValidatorTest {
	protected FormValidator formvalid;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		formvalid = new FormValidator();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetYear() {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(1800, 9, 10);
		assertFalse(formvalid.getYear(calendar.getTime()));
	}

	@Test
	public void testParseSearch() {
		String str1 = "Mo_e%";
		String str2 = "Mo?e*";
		assertEquals("Not eguals", str1, formvalid.parseSearch(str2));
	}

}
