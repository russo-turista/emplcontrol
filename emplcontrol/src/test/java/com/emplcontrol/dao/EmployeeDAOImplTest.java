/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emplcontrol.dao;

import com.emplcontrol.domain.Division;
import com.emplcontrol.domain.Employees;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.sql.DataSource;
import static org.junit.Assert.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Filippov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/ApplicationTest-context.xml"})
@Transactional
public class EmployeeDAOImplTest {

    @Inject
    private EmployeeDAOImpl emplDaoImpl;
    @Inject
    private Employees emplTest;
    @Inject
    private Division divTest;
    private SimpleJdbcTemplate jdbcTemplate;
    private SimpleDateFormat formatDate;
    protected String firstNameTest = "Joe";
    protected String lastNameTest = "Smith";
    protected String divisionTest1 = "Upravlenie1";
    protected String divisionTest2 = "Upravlenie2";
    protected Date birthdateTest;
    protected double salary = 1000.00;
    protected Boolean activeTest = true;
    private Calendar calendar;

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        assertNotNull("Expected pojo to be set", emplDaoImpl);
        calendar = GregorianCalendar.getInstance();
        calendar.set(1984, 10, 10);
        formatDate = new SimpleDateFormat("yyyy-MM-dd");
        birthdateTest = calendar.getTime();
        emplTest.setFirstName(firstNameTest);
        emplTest.setLastName(lastNameTest);
        emplTest.setActive(activeTest);
        emplTest.setBirthdate(calendar.getTime());
        emplTest.setSalary(salary);
        emplTest.setSearchFirstName(firstNameTest);
        emplTest.setSearchLastName(lastNameTest);
        divTest.setNameDiv(divisionTest1);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of setDataSource method, of class EmployeeDAOImpl.
     */
    @Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    @Test
    public void testSetDataSource() {
        assertNotNull(jdbcTemplate.getJdbcOperations());
    }

    /**
     * Test of getAll method, of class EmployeeDAOImpl.
     */
    @Test
    public void testGetAll() {
        emplDaoImpl.addDiv(divisionTest1);
        emplDaoImpl.addEmpl(firstNameTest, lastNameTest, emplDaoImpl.getMaxDivId(), birthdateTest, salary, activeTest);
        for (Employees employeesList : emplDaoImpl.getAll()) {
            if (employeesList.getEmplId().intValue() == emplDaoImpl.getMaxEmplId() && employeesList.getEmplDiv().intValue() == emplDaoImpl.getMaxDivId()) {
                assertEquals(employeesList.getFirstName(), emplTest.getFirstName());
                assertEquals(employeesList.getLastName(), emplTest.getLastName());
                assertEquals(formatDate.format(emplTest.getBirthdate()), formatDate.format(employeesList.getBirthdate()));
                assertEquals(employeesList.getSalary(), emplTest.getSalary());
                assertEquals(employeesList.getActive(), emplTest.getActive());
            }
        }
    }

    /**
     * Test of getAllDivision method, of class EmployeeDAOImpl.
     */
    @Test
    public void testGetAllDivision() {
        emplDaoImpl.addDiv(divisionTest1);
        for (Division divisionList : emplDaoImpl.getAllDivision()) {
            if (divisionList.getDivId().intValue() == emplDaoImpl.getMaxDivId()) {
                assertEquals(divisionList.getNameDiv(), divTest.getNameDiv());
            }
        }

    }

    /**
     * Test of getEmpl method, of class EmployeeDAOImpl.
     */
    @Test
    public void testGetEmpl() {
        emplDaoImpl.addDiv(divisionTest1);
        emplDaoImpl.addEmpl(firstNameTest, lastNameTest, emplDaoImpl.getMaxDivId(), birthdateTest, salary, activeTest);
        Employees employeesList;
        employeesList = emplDaoImpl.getEmpl(emplDaoImpl.getMaxEmplId());
        assertEquals(employeesList.getFirstName(), emplTest.getFirstName());
        assertEquals(employeesList.getLastName(), emplTest.getLastName());
        assertEquals(employeesList.getBirthdate().toString(), formatDate.format(emplTest.getBirthdate()));
        assertEquals(employeesList.getSalary(), emplTest.getSalary());
        assertEquals(employeesList.getActive(), emplTest.getActive());
    }

    /**
     * Test of getDiv method, of class EmployeeDAOImpl.
     */
    @Test
    public void testGetDiv() {
        emplDaoImpl.addDiv(divisionTest1);
        assertNotNull("True is false", emplDaoImpl.getDiv(emplDaoImpl.getMaxDivId()));
    }

    /**
     * Test of editDiv method, of class EmployeeDAOImpl.
     */
    @Test
    public void testEditDiv() {
        emplDaoImpl.addDiv(divisionTest1);
        Division div1;
        Division div2;
        div1 = emplDaoImpl.getDiv(emplDaoImpl.getMaxDivId());
        emplDaoImpl.editDiv(div1.getDivId(), "Новое название отдела");
        div2 = emplDaoImpl.getDiv(div1.getDivId());
        assertTrue(div1.getDivId().equals(div2.getDivId()));
        assertFalse(div1.getNameDiv().equals(div2.getNameDiv()));
    }

    /**
     * Test of getSearch method, of class EmployeeDAOImpl.
     */
    @Test
    public void testGetSearch() {
        emplDaoImpl.addDiv(divisionTest1);
        emplDaoImpl.addEmpl(firstNameTest, lastNameTest, emplDaoImpl.getMaxDivId(), birthdateTest, salary, activeTest);
        assertNotNull("GetSearch not work", emplDaoImpl.getSearch(firstNameTest, lastNameTest));
    }

    /**
     * Test of addEmpl method, of class EmployeeDAOImpl.
     */
    @Test
    public void testAddEmpl() {
        emplDaoImpl.addDiv(divisionTest1);
        emplDaoImpl.addEmpl(firstNameTest, lastNameTest, emplDaoImpl.getMaxDivId(), birthdateTest, salary, activeTest);
        Employees employeesList;
        employeesList = emplDaoImpl.getEmpl(emplDaoImpl.getMaxEmplId());
        assertEquals(employeesList.getFirstName(), emplTest.getFirstName());
        assertEquals(employeesList.getLastName(), emplTest.getLastName());
        assertEquals(employeesList.getBirthdate().toString(), formatDate.format(emplTest.getBirthdate()));
        assertEquals(employeesList.getSalary(), emplTest.getSalary());
        assertEquals(employeesList.getActive(), emplTest.getActive());
    }

    /**
     * Test of addDiv method, of class EmployeeDAOImpl.
     */
    @Test
    public void testAddDiv() {
        emplDaoImpl.addDiv(divisionTest1);
        Division div;
        div = emplDaoImpl.getDiv(emplDaoImpl.getMaxDivId());
        assertEquals(div.getNameDiv(), divTest.getNameDiv());
    }

    /**
     * Test of editEmpl method, of class EmployeeDAOImpl.
     */
    @Test
    public void testEditEmpl() {
        Employees empl1;
        Employees empl2;

        Calendar calendar1 = GregorianCalendar.getInstance();
        calendar1.set(1975, 10, 10);


        emplDaoImpl.addDiv(divisionTest1);
        emplDaoImpl.addEmpl(firstNameTest, lastNameTest, emplDaoImpl.getMaxDivId(), birthdateTest, salary, activeTest);
        empl1 = emplDaoImpl.getEmpl(emplDaoImpl.getMaxEmplId());

        emplDaoImpl.addDiv("Новый отдел");
        emplDaoImpl.editEmpl(emplDaoImpl.getMaxEmplId(), "Пронин", "Михаил", emplDaoImpl.getMaxDivId(), calendar1.getTime(), false, 1.00);
        empl2 = emplDaoImpl.getEmpl(emplDaoImpl.getMaxEmplId());

        assertTrue(empl1.getEmplId().equals(empl2.getEmplId()));
        assertFalse(empl1.getEmplDiv().equals(empl2.getEmplDiv()));
        assertFalse(empl1.getActive().equals(empl2.getActive()));
        assertFalse(empl1.getFirstName().equals(empl2.getFirstName()));
        assertFalse(empl1.getLastName().equals(empl2.getLastName()));
        assertFalse(empl1.getBirthdate().equals(empl2.getBirthdate()));
        assertFalse(empl1.getSalary().equals(empl2.getSalary()));
    }

    /**
     * Test of getMaxEmplCounter method, of class EmployeeDAOImpl.
     */
    @Test
    public void testGetMaxEmplCounter() {
        emplDaoImpl.addDiv(divisionTest1);
        emplDaoImpl.addEmpl(firstNameTest, lastNameTest, emplDaoImpl.getMaxDivId(), birthdateTest, salary, activeTest);
        assertNotNull(emplDaoImpl.getMaxEmplCounter());
    }

    @Test
    public void testGetMaxDivId() {
        emplDaoImpl.addDiv(divisionTest1);
        int i1 = emplDaoImpl.getMaxDivId();
        emplDaoImpl.addDiv(divisionTest2);
        int i2 = emplDaoImpl.getMaxDivId();
        assertFalse(i1 > i2);
    }

    @Test
    public void testGetMaxEmplId() {
        emplDaoImpl.addDiv(divisionTest1);
        emplDaoImpl.addEmpl(firstNameTest, lastNameTest, emplDaoImpl.getMaxDivId(), birthdateTest, salary, activeTest);
        int i1 = emplDaoImpl.getMaxEmplId();
        emplDaoImpl.addDiv(divisionTest2);
        emplDaoImpl.addEmpl(firstNameTest, lastNameTest, emplDaoImpl.getMaxDivId(), birthdateTest, salary, activeTest);
        int i2 = emplDaoImpl.getMaxEmplId();
        assertTrue(i2 > i1);
    }
    @Test
    public void testIsValidDiv() {
        emplDaoImpl.addDiv(divisionTest1);
        assertFalse(emplDaoImpl.isValidDivName(divisionTest1));
    }
}
