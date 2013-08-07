package com.emplcontrol.controller;

import org.apache.struts.mock.MockHttpServletRequest;
import org.apache.struts.mock.MockHttpServletResponse;
import static org.junit.Assert.assertEquals;
import org.junit.*;
import org.springframework.ui.ExtendedModelMap;

public class LoginControllerTest {
	
	protected LoginController loginController;
	protected MockHttpServletRequest request;
	protected MockHttpServletResponse response;
	protected ExtendedModelMap model;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		model = new ExtendedModelMap();
		loginController = new LoginController();
	}

	@After
	public void tearDown() throws Exception {
		
	}
	@Test
	public void testLogin() {		
		assertEquals("login", loginController.login(model));
	}
        @Test 
        public void testStartpage(){
            assertEquals("login", loginController.startPage(model));
        }
        @Test 
        public void testLoginerror(){
            assertEquals("login", loginController.loginError(model));
        }
        @Test 
        public void Logout(){
            assertEquals("login", loginController.logout(model));
        }


}
