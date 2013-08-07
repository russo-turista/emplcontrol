/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emplcontrol.dao;

import com.emplcontrol.domain.Division;
import com.emplcontrol.domain.Employees;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.sql.DataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * Реализация интрефейса {@link EmployeeDAO}
 */
@Repository
public class EmployeeDAOImpl implements EmployeeDAO {

    /**
     * объект калсса  {@link  Employees}
     */
    @Inject
    private Employees employees;
    /**
     *
     * объект класса  {@link  Division}
     */
    @Inject
    private Division division;
    /**
     * переменная типа {@link String} используется для передачи sql зпароса в БД
     */
    private String sql = "";
    /**
     * переменная типа {@link SimpleJdbcTemplate} экземпляр класса
     * SimpleJdbcTemplate
     */
    private SimpleJdbcTemplate jdbcTemplate;

    /**
     * Реализуем jdbcTemplate, передаем параметры соединения
     *
     * @param dataSource {@link DataSource}
     */
    @Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }
    /**
     * объект класса {@link RowMapper} Связывает возвращаемые запросом данные с {@link Employees}
     *
     * @return  {@link Employees}- с полученными данными из БД
     */
    private RowMapper<Employees> rowMapperEmpl = new RowMapper<Employees>() {

        public Employees mapRow(ResultSet rs, int rowNum) throws SQLException {
            employees = new Employees();
            employees.setEmplId(rs.getInt("emplId"));
            employees.setFirstName(rs.getString("firstName"));
            employees.setLastName(rs.getString("lastName"));
            employees.setEmplDiv(rs.getInt("emplDiv"));
            employees.setSalary(rs.getDouble("salary"));
            employees.setBirthdate(rs.getDate("birthdate"));
            employees.setActive(rs.getBoolean("active"));
            return employees;
        }
    };
    /**
     * объект класса {@link RowMapper} Связывает возвращаемые запросом данные с {@link Division}
     *
     * @return  {@link Division}- с полученными данными из БД
     */
    private RowMapper<Division> rowMapperDiv = new RowMapper<Division>() {

        public Division mapRow(ResultSet rs, int rowNum) throws SQLException {
            division = new Division();
            division.setDivId(rs.getInt("divId"));
            division.setNameDiv(rs.getString("nameDiv"));
            return division;
        }
    };

    /**
     * {@inheritDoc}
     */
    public List<Employees> getAll() {
        sql = "select * from empl";
        return jdbcTemplate.query(sql, rowMapperEmpl);
    }

    /**
     * {@inheritDoc}
     */
    public List<Division> getAllDivision() {
        sql = "select divId,nameDiv from division";
        return jdbcTemplate.query(sql, rowMapperDiv);
    }

    /**
     * {@inheritDoc}
     */
    public Employees getEmpl(Integer emplId) {
        sql = "SELECT emplId,firstName,lastName,emplDiv, salary, active, birthdate from empl WHERE  empl.emplId=?";
        return jdbcTemplate.queryForObject(sql, rowMapperEmpl, emplId);
    }

    /**
     * {@inheritDoc}
     */
    public Division getDiv(Integer divId) {
        sql = "SELECT divId, nameDiv from division WHERE  divId = ?";
        return jdbcTemplate.queryForObject(sql, rowMapperDiv, divId);
    }

    /**
     * {@inheritDoc}
     */
    public void editDiv(Integer divId, String nameDiv) {
        sql = "update division set nameDiv = :nameDiv where divId = :divId";

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("divId", divId);
        parameters.put("nameDiv", nameDiv);
        jdbcTemplate.update(sql, parameters);
    }

    /**
     * {@inheritDoc}
     */
    public List<Employees> getSearch(String firstName, String lastName) {
        sql = "SELECT *  from empl WHERE firstname LIKE ? AND lastName LIKE ?";
        Object[] parameters = new Object[]{firstName, lastName};
        return jdbcTemplate.query(sql, rowMapperEmpl, parameters);
    }

    /**
     * {@inheritDoc}
     */
    public void addEmpl(String firstName, String lastName, Integer emplDiv, Date birthdate, Double salary, Boolean active) {
        sql = "insert into empl(firstName, lastName, emplDiv, salary, birthdate, active) values "
                + "(:firstName, :lastName, :emplDiv, :salary, :birthdate, :active);";
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("firstName", firstName);
        parameters.put("lastName", lastName);
        parameters.put("emplDiv", emplDiv);
        parameters.put("birthdate", birthdate);
        parameters.put("salary", salary);
        parameters.put("active", active);

        jdbcTemplate.update(sql, parameters);
    }

    /**
     * {@inheritDoc}
     */
    public void addDiv(String nameDiv) {
        sql = "insert into division(nameDiv) values "
                + "(:nameDiv)";
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("nameDiv", nameDiv);
        jdbcTemplate.update(sql, parameters);
    }

    /**
     * {@inheritDoc}
     */
    public void editEmpl(Integer emplId, String firstName, String lastName, Integer emplDiv, Date birthdate, Boolean active, Double salary) {
        sql = "update empl set firstname = :firstName, "
                + "lastname = :lastName, emplDiv = :emplDiv, salary = :salary, birthdate = :birthdate, active = :active where empl.emplId = :emplId";

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("emplId", emplId);
        parameters.put("firstName", firstName);
        parameters.put("lastName", lastName);
        parameters.put("emplDiv", emplDiv);
        parameters.put("birthdate", birthdate);
        parameters.put("active", active);
        parameters.put("salary", salary);

        jdbcTemplate.update(sql, parameters);
    }

    /**
     * {@inheritDoc}
     */
    public Boolean getMaxEmplCounter() {
        return jdbcTemplate.queryForInt("select COUNT(*) from empl") < 5000 ? true : false;
    }

    /**
     * {@inheritDoc}
     */
    public int getMaxDivId() {
        return jdbcTemplate.queryForInt("select MAX(divId) from division");
    }

    /**
     * {@inheritDoc}
     */
    public int getMaxEmplId() {
        return jdbcTemplate.queryForInt("select MAX(emplId) from empl");
    }
    
   /**
     * {@inheritDoc}
     */
    public boolean isValidDivName(String divName, int divId){
        sql = "SELECT *  from division WHERE nameDiv = ? AND divid != ?";
        Object[] parameters = new Object[]{divName, divId};
        if (jdbcTemplate.query(sql, rowMapperDiv, parameters).isEmpty())
            return true;
        else 
            return false;
    }
    /**
     * {@inheritDoc}
     */
    public Boolean isValidDivName(String divName){
        sql = "SELECT *  from division WHERE  nameDiv = ?";
        Object[] parameters = new Object[]{divName};
        if (jdbcTemplate.query(sql, rowMapperDiv, parameters).isEmpty())
            return true;
        else 
            return false;
    }
}
