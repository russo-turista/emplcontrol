/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emplcontrol.controller;

import com.emplcontrol.domain.Division;
import com.emplcontrol.domain.Employees;
import com.emplcontrol.service.EmployeeService;
import com.emplcontrol.service.FormValidator;
import com.emplcontrol.service.StringForm;
import java.util.*;
import javax.inject.Inject;
import static org.junit.Assert.assertEquals;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/ApplicationTest-context.xml")
@Transactional
public class MainControllerTest {

    protected Integer emplId;
    protected Integer divId;
    protected String firstNameTest = "Joe";
    protected String lastNameTest = "Smith";
    protected String divisionTest = "Upravlenie";
    protected Date birthdateTest;
    protected double salaryTest = 1000.00;
    protected Boolean activeTest = true;
    @Inject
    private MainController mainController;
    @Inject
    private Employees employeesTest;
    @Inject
    private FormValidator formValidator;
    @Inject
    private StringForm stringForm;
    @Inject
    private Employees employees;
    @Inject
    private ApplicationContext applicationContext;
    @Inject
    private EmployeeService employeeService;
    private EmployeeService emplServMock;
  
    @Inject
    private Division formDiv;
    @Mock
    private BindingResult mockBindingResult;
    protected ExtendedModelMap model;

    public MainControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        model = new ExtendedModelMap();
        MockitoAnnotations.initMocks(this);
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(10, 12, 1984);
        birthdateTest = calendar.getTime();

    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetEmpl() throws Exception {

        String mav = mainController.getEmpl(model);
        assertEquals("emplpage", mav);

    }

    /**
     * Test of getSerchRes method, of class MainController.
     */
    @Test
    public void testGetSerchRes() {
        employees.setSearchFirstName("Петр");
        employees.setSearchLastName("Прокофьев");
        ModelAndView mav = mainController.getSerchRes(employees);
        assertEquals("redirect:../startpage", mav.getViewName());


    }

    /**
     * Test of getSerchResEmpl method, of class MainController.
     */
    @Test
    public void testGetSerchResEmpl() {

        ModelAndView mav = mainController.getSerchResEmpl();

        ModelAndViewAssert.assertAndReturnModelAttributeOfType(mav, "userRole", Boolean.class);
        ModelAndViewAssert.assertAndReturnModelAttributeOfType(mav, "employees", List.class);
        ModelAndViewAssert.assertModelAttributeAvailable(mav, "userRole");
        ModelAndViewAssert.assertModelAttributeAvailable(mav, "employees");
        ModelAndViewAssert.assertModelAttributeValue(mav, "userRole", false);
        ModelAndViewAssert.assertViewName( mav, "emplpage");
    }

    /**
     * Test of getEmployeeId method, of class MainController.
     */
    @Test
    public void testGetEmployeeId() {
        employeeService.addDiv(divisionTest);
        divId = employeeService.getMaxDivId();
        employeeService.addEmpl(firstNameTest, lastNameTest, divId, birthdateTest, salaryTest, activeTest);
        emplId = employeeService.getMaxEmplId();

        String mavString = mainController.getEmployee(emplId, model);
        assertEquals("emplpagefull", mavString);
    }

    /**
     * Test of getAddEmployee_Req method, of class MainController.
     */
    @Test
    public void testGetAddEmployeeReq() {
        ModelAndView mav = mainController.getAddEmployeeReq();
        ModelAndViewAssert.assertAndReturnModelAttributeOfType(mav, "formEmpl", Employees.class);
        ModelAndViewAssert.assertAndReturnModelAttributeOfType(mav, "division", LinkedHashMap.class);
        ModelAndViewAssert.assertModelAttributeValue(mav, "mesYearFlag", false);
        ModelAndViewAssert.assertModelAttributeAvailable(mav, "division");
        ModelAndViewAssert.assertModelAttributeAvailable(mav, "mesYearFlag");
        ModelAndViewAssert.assertModelAttributeAvailable(mav, "formEmpl");
        ModelAndViewAssert.assertViewName(mav, "addEditEmpl");

    }

    @Test
    public void testGetEditEmployeeReq() {
        employeeService.addDiv(divisionTest);
        divId = employeeService.getMaxDivId();
        employeeService.addEmpl(firstNameTest, lastNameTest, divId, birthdateTest, salaryTest, activeTest);
        emplId = employeeService.getMaxEmplId();

        ModelAndView mav = mainController.getEditEmployeeReq(emplId);

        ModelAndViewAssert.assertAndReturnModelAttributeOfType(mav, "formEmpl", Employees.class);
        ModelAndViewAssert.assertModelAttributeAvailable(mav, "mesYearFlag");
        ModelAndViewAssert.assertModelAttributeValue(mav, "mesYearFlag", false);
        ModelAndViewAssert.assertViewName(mav, "addEditEmpl");

    }

    /**
     * Test of getDivision method, of class MainController.
     */
    @Test
    public void testGetDivision() {
        String mavString = mainController.getDivision(model);
        assertEquals("divisionlist", mavString);

    }

    /**
     * Test of addDiv method, of class MainController.
     */
    @Test
    public void testAddDiv() throws Exception {
        ModelAndView mav = mainController.addDiv();
        ModelAndViewAssert.assertAndReturnModelAttributeOfType(mav, "formDiv", Division.class);
        ModelAndViewAssert.assertAndReturnModelAttributeOfType(mav, "mesDiv", Boolean.class);
        ModelAndViewAssert.assertModelAttributeAvailable(mav, "formDiv");
        ModelAndViewAssert.assertModelAttributeAvailable(mav, "mesDiv");
        ModelAndViewAssert.assertModelAttributeValue(mav, "mesDiv", false);
        ModelAndViewAssert.assertViewName( mav, "addDiv");
    }

    @Test
    public void testAddDivRes() {

        Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
        ModelAndView resultTrue = mainController.addDivRes(formDiv, mockBindingResult);
        assertEquals("addDiv", resultTrue.getViewName());

    }

    @Rollback(true)
    @Test
    public void testAddDivRes2() {
        employeeService.addDiv(divisionTest);
        divId = employeeService.getMaxDivId();
        Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
        ModelAndView resultFalse = mainController.addDivRes(employeeService.getDiv(divId), mockBindingResult);
        Mockito.when(employeeService.isValidDivName(employeeService.getDiv(divId).getNameDiv(), divId)).thenReturn(true);
        assertEquals("addDiv", resultFalse.getViewName());
    }

    /**
     * Test of getEditDiv_Req method, of class MainController.
     */
    @Test
    public void testGetEditDivReq() {
        employeeService.addDiv(divisionTest);
        divId = employeeService.getMaxDivId();

        ModelAndView mav = mainController.getEditDivReq(divId);
        ModelAndViewAssert.assertAndReturnModelAttributeOfType(mav, "formDiv", Division.class);
        ModelAndViewAssert.assertAndReturnModelAttributeOfType(mav, "division", Division.class);
        ModelAndViewAssert.assertAndReturnModelAttributeOfType(mav, "mesDiv", Boolean.class);
        ModelAndViewAssert.assertModelAttributeAvailable(mav, "formDiv");
        ModelAndViewAssert.assertModelAttributeAvailable(mav, "mesDiv");
        ModelAndViewAssert.assertModelAttributeAvailable(mav, "division");
        ModelAndViewAssert.assertModelAttributeValue(mav, "mesDiv", false);
        ModelAndViewAssert.assertViewName(mav, "editeDiv");
    }

    /**
     * Test of getEditDiv_Res method, of class MainController.
     */
    @Test
    public void testGetEditDivRes() {
        employeeService.addDiv(divisionTest);
        List<Division> division =  employeeService.getAllDivision();
        for (Division c : division) {
            System.out.println("divId=" + c.getDivId()+"  "+ "divName= " + c.getNameDiv());
            
        }
        divId = employeeService.getMaxDivId();
        Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
        ModelAndView mav = mainController.getEditDivRes(divId, employeeService.getDiv(divId), mockBindingResult);
        ModelAndViewAssert.assertViewName(mav, "redirect:../../../division/");


    }

    @Test
    public void testGetEditDivRes2() {
        employeeService.addDiv(divisionTest);
        divId = employeeService.getMaxDivId();
        Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
        ModelAndView mav = mainController.getEditDivRes(divId, formDiv, mockBindingResult);
        ModelAndViewAssert.assertViewName(mav, "editeDiv");

    }
}
