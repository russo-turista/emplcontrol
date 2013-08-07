package com.emplcontrol.service;

import com.emplcontrol.domain.Division;
import com.emplcontrol.domain.Employees;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.inject.Inject;
import static org.junit.Assert.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/ApplicationTest-context.xml"})
@Transactional
public class EmployeeServiceTest {

    @Inject
    private EmployeeService emplService;
    @Inject
    private Employees emplTest;
    @Inject
    private Division divTest;
    private SimpleDateFormat formatDate;
    protected static String firstNameTest = "Joe";
    protected static String lastNameTest = "Smith";
    protected static String divisionTest1 = "Upravlenie1";
    protected static String divisionTest2 = "Upravlenie2";
    protected Date birthdateTest;
    protected double salary = 1000.00;
    protected static Boolean active = true;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
       
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
       
    }

    @Before
    public void setUp() throws Exception {
        assertNotNull("Expected pojo to be set", emplService);
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(1984, 10, 10);
        formatDate = new SimpleDateFormat("yyyy-MM-dd");
        birthdateTest = calendar.getTime();
        emplTest.setFirstName(firstNameTest);
        emplTest.setLastName(lastNameTest);
        emplTest.setActive(active);
        emplTest.setBirthdate(birthdateTest);
        emplTest.setSalary(salary);
        emplTest.setSearchFirstName(firstNameTest);
        emplTest.setSearchLastName(lastNameTest);
        divTest.setNameDiv(divisionTest1);
    }

    @After
    public void tearDown() throws Exception {
       
    }

    @Test
    public void testGetAll() {
        emplService.addDiv(divisionTest1);
        emplService.addEmpl(firstNameTest, lastNameTest, emplService.getMaxDivId(), birthdateTest, salary, active);

        for (Employees employeesList : emplService.getAll()) {
            if (employeesList.getEmplId() == emplService.getMaxEmplId() && employeesList.getEmplDiv() == emplService.getMaxDivId()) {
                assertEquals(employeesList.getFirstName(), emplTest.getFirstName());
                assertEquals(employeesList.getLastName(), emplTest.getLastName());
                assertEquals(formatDate.format(emplTest.getBirthdate()), employeesList.getBirthdate().toString());
                assertEquals(employeesList.getSalary(), emplTest.getSalary());
                assertEquals(employeesList.getActive(), emplTest.getActive());
            }
        }

    }

    @Test
    public void testGetAllDivision() {
        emplService.addDiv(divisionTest1);
        for (Division divisionList : emplService.getAllDivision()) {
            if (divisionList.getDivId() == emplService.getMaxDivId()) {
                assertEquals(divisionList.getNameDiv(), divTest.getNameDiv());
            }
        }

    }

    @Test
    public void testAddDiv() {
        emplService.addDiv(divisionTest1);
        Division div;
        div = emplService.getDiv(emplService.getMaxDivId());
        assertEquals(div.getNameDiv(), divTest.getNameDiv());
    }

    @Test
    public void testGetEmpl() {
        emplService.addDiv(divisionTest1);
        emplService.addEmpl(firstNameTest, lastNameTest, emplService.getMaxDivId(), birthdateTest, salary, active);
        Employees employeesList;
        employeesList = emplService.getEmpl(emplService.getMaxEmplId());
        assertEquals(employeesList.getFirstName(), emplTest.getFirstName());
        assertEquals(employeesList.getLastName(), emplTest.getLastName());
        assertEquals(employeesList.getBirthdate().toString(), formatDate.format(emplTest.getBirthdate()));
        assertEquals(employeesList.getSalary(), emplTest.getSalary());
        assertEquals(employeesList.getActive(), emplTest.getActive());
    }

    @Test
    public void testAddEmpl() {
        emplService.addDiv(divisionTest1);
        emplService.addEmpl(firstNameTest, lastNameTest, emplService.getMaxDivId(), birthdateTest, salary, active);
        Employees employeesList;
        employeesList = emplService.getEmpl(emplService.getMaxEmplId());
        assertEquals(employeesList.getFirstName(), emplTest.getFirstName());
        assertEquals(employeesList.getLastName(), emplTest.getLastName());
        assertEquals(employeesList.getBirthdate().toString(), formatDate.format(emplTest.getBirthdate()));
        assertEquals(employeesList.getSalary(), emplTest.getSalary());
        assertEquals(employeesList.getActive(), emplTest.getActive());
    }

    @Test
    public void testGetDiv() {
        emplService.addDiv(divisionTest1);
        assertEquals(emplService.getDiv((emplService.getMaxDivId())).getNameDiv(), divTest.getNameDiv());
    }

    @Test
    public void testEditDiv() {
        emplService.addDiv(divisionTest1);
        Division div1;
        Division div2;
        div1 = emplService.getDiv(emplService.getMaxDivId());
        emplService.editDiv(div1.getDivId(), "Новое название отдела");
        div2 = emplService.getDiv(div1.getDivId());
        assertTrue(div1.getDivId().equals(div2.getDivId()));
        assertFalse(div1.getNameDiv().equals(div2.getNameDiv()));
    }

    @Test
    public void testGetSearch() {
        emplService.addDiv(divisionTest1);
        emplService.addEmpl(firstNameTest, lastNameTest, emplService.getMaxDivId(), birthdateTest, salary, active);
        assertNotNull("GetSearch work", emplService.getSearch(firstNameTest, lastNameTest));
    }
    @Test
    public void testGetSearch1() {
        emplService.addDiv(divisionTest1);
        emplService.addEmpl(firstNameTest, lastNameTest, emplService.getMaxDivId(), birthdateTest, salary, active);
        for (Employees employeesList :  emplService.getSearch("Jo_", "Smi__")) {   
            if (employeesList.getEmplId() == emplService.getMaxEmplId()) {  
                System.out.println("Search= " + employeesList.getFirstName());
                System.out.println("CaonstEmpl= " + emplTest.getFirstName()); 
                assertEquals(employeesList.getFirstName(), emplTest.getFirstName());
                assertEquals(employeesList.getLastName(), emplTest.getLastName());
                assertEquals(formatDate.format(emplTest.getBirthdate()), employeesList.getBirthdate().toString());
                assertEquals(employeesList.getSalary(), emplTest.getSalary());
                assertEquals(employeesList.getActive(), emplTest.getActive());
            }
        }
    }
    @Test
    public void testGetSearch2() {
        emplService.addDiv(divisionTest1);
        emplService.addEmpl(firstNameTest, lastNameTest, emplService.getMaxDivId(), birthdateTest, salary, active);
        for (Employees employeesList :  emplService.getSearch("_oe", "S%")) {   
            if (employeesList.getEmplId() == emplService.getMaxEmplId()) {  
                System.out.println("Search= " + employeesList.getFirstName());
                System.out.println("CaonstEmpl= " + emplTest.getFirstName()); 
                assertEquals(employeesList.getFirstName(), emplTest.getFirstName());
                assertEquals(employeesList.getLastName(), emplTest.getLastName());
                assertEquals(formatDate.format(emplTest.getBirthdate()), employeesList.getBirthdate().toString());
                assertEquals(employeesList.getSalary(), emplTest.getSalary());
                assertEquals(employeesList.getActive(), emplTest.getActive());
            }
        }
    }

    @Test
    public void testEditEmpl() {

        Employees empl1;
        Employees empl2;

        Calendar calendar1 = GregorianCalendar.getInstance();
        calendar1.set(1975, 10, 10);


        emplService.addDiv(divisionTest1);
        emplService.addEmpl(firstNameTest, lastNameTest, emplService.getMaxDivId(), birthdateTest, salary, active);
        empl1 = emplService.getEmpl(emplService.getMaxEmplId());

        emplService.addDiv("Новый отдел");
        emplService.editEmpl(emplService.getMaxEmplId(), "Пронин", "Михаил", emplService.getMaxDivId(), calendar1.getTime(), false, 1.00);
        empl2 = emplService.getEmpl(emplService.getMaxEmplId());

        assertTrue(empl1.getEmplId().equals(empl2.getEmplId()));
        assertFalse(empl1.getEmplDiv().equals(empl2.getEmplDiv()));
        assertFalse(empl1.getActive().equals(empl2.getActive()));
        assertFalse(empl1.getFirstName().equals(empl2.getFirstName()));
        assertFalse(empl1.getLastName().equals(empl2.getLastName()));
        assertFalse(empl1.getBirthdate().equals(empl2.getBirthdate()));
        assertFalse(empl1.getSalary().equals(empl2.getSalary()));

    }
     @Test
    public void testIsValidDiv() {
        emplService.addDiv(divisionTest1);
        assertFalse(emplService.isValidDivName(divisionTest1));
    }

   @Test
    public void testGetMaxEmployeesIdCounter() {
        emplService.addDiv(divisionTest1);
        emplService.addEmpl(firstNameTest, lastNameTest, emplService.getMaxDivId(), birthdateTest, salary, active);
        assertTrue(emplService.getMaxEmplCounter());
    }

    @Test
    public void testGetMaxDivId() {
        emplService.addDiv(divisionTest1);
        int i1 = emplService.getMaxDivId();
        emplService.addDiv(divisionTest2);
        int i2 = emplService.getMaxDivId();
        assertTrue(i2 > i1);
    }

    @Test
    public void testGetMaxEmplId() {
        emplService.addDiv(divisionTest1);
        emplService.addEmpl(firstNameTest, lastNameTest, emplService.getMaxDivId(), birthdateTest, salary, active);
        int i1 = emplService.getMaxEmplId();
        emplService.addDiv(divisionTest2);
        emplService.addEmpl(firstNameTest, lastNameTest, emplService.getMaxDivId(), birthdateTest, salary, active);
         int i2 = emplService.getMaxEmplId();
        assertTrue(i2 > i1);
    }
}
