/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emplcontrol.controller;

import com.emplcontrol.service.EnterDb;
import java.text.ParseException;
import javax.inject.Inject;
import static org.easymock.EasyMock.*;
import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/ApplicationTest-context.xml"})
@Transactional
public class CreateDBControllerTest {

    @Mock private EnterDb enterDb;
    @Inject private EnterDb mockDb;
    protected ExtendedModelMap model;
    @Inject private CreateDBController createDBController;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private HandlerAdapter handlerAdapter;

    public CreateDBControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        model = new ExtendedModelMap();
        MockitoAnnotations.initMocks(this);
        model = new ExtendedModelMap();

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of login method, of class CreateDBController.
     * @throws ParseException 
     */
    @Test
    public void TestCreateDBcontrl() throws ParseException {
       
        enterDb = mock(EnterDb.class);
        Mockito.when(enterDb.createDb(10)).thenReturn(Boolean.TRUE);
        ModelAndView mav = createDBController.createDBcontrl();
        assertEquals("redirect:/startpage", mav.getViewName());

    }
}
