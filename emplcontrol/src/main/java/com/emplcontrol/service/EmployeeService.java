package com.emplcontrol.service;

import com.emplcontrol.dao.EmployeeDAO;
import com.emplcontrol.domain.Division;
import com.emplcontrol.domain.Employees;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import javax.inject.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * Реализация работы с базой данных
 */
@Service
@Transactional(readOnly = true)
public class EmployeeService {

    /**
     * объект калсса {@link EmployeeDAO}
     */
    @Autowired
    private EmployeeDAO employeeDAO;
    /**
     * объект класса {@link StringForm}
     */ 
    @Inject
    private StringForm stringFrom;

    /**
     * Извлекает из БД полный список сотрудников
     * @return {@link List}- список сотрудников
     */
    public List<Employees> getAll() {
        return employeeDAO.getAll();
    }

    /**
     * Извлекает из БД полный списока отделов
     * @return {@link List} - список отделов
     */
    public List<Division> getAllDivision() {
        return employeeDAO.getAllDivision();
    }

    /**
     * Извлекает из БД данные по отдельному сотруднику
     * @param emplId {@link Integer} индекс сотрудника
     * @return {@link Employees} - данные сотрудника
     */
    public Employees getEmpl(Integer emplId) {
        return employeeDAO.getEmpl(emplId);
    }

    /**
     * Извлекает из БД данные по определенному отделу
     * @param divId {@link Integer} индекс отдела
     * @return {@link Division}- данные отдела
     */
    public Division getDiv(Integer divId) {
        return employeeDAO.getDiv(divId);
    }

    /**
     * Перезаписывает данные в БД по определенному отделу
     * @param divId {@link Integer} индекс отдела
     * @param nameDiv {@link String} название отдела
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void editDiv(Integer divId, String nameDiv) {
        employeeDAO.editDiv(divId, nameDiv);
    }

    /**
     * Реализует поиск сотрудников в БД
     * @param firstName {@link String} запрашиваемое имя сотрудника
     * @param lastName {@link String}  запрашиваемая фамилия сотрудника
     * @return {@link List}- возваращает список сотрдуников соответствующих
     * введемным параметрам
     */
    public List<Employees> getSearch(String firstName, String lastName) {
        return employeeDAO.getSearch(firstName, lastName);
    }

    /**
     * Сохраняет в БД нового сотрудника
     * @param firstName {@link String}- имя нового сотрудника
     * @param lastName {@link String}- фамилия нового сотрудника
     * @param emplDiv  {@link Integer}- номер отдела сотрудника 
     * @param birthdate {@link Date}- день рождения сотрудника
     * @param salary {@link Double}- зарплата сотрудника
     * @param active {@link Boolean}- статус активности
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void addEmpl(String firstName, String lastName, Integer emplDiv, Date birthdate, Double salary, Boolean active) {
        employeeDAO.addEmpl(firstName, lastName, emplDiv, birthdate, salary, active);
    }

    /**
     *Сохраняет в БД новый отдел
     * @param nameDiv {@link Integer} имя нового отдела
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void addDiv(String nameDiv) {
        employeeDAO.addDiv(nameDiv);
    }

    /**
     * Вводит изменения по существующиму сотруднику
     * @param emplId {@link Integer}- id сотрдуника
     * @param firstName {@link String}- имя  сотрудника
     * @param lastName {@link String}- фамилия  сотрудника
     * @param emplDiv  {@link Integer}- номер отдела сотрудника 
     * @param birthdate {@link Date}- день рождения сотрудника
     * @param salary {@link Double}- зарплата сотрудника
     * @param active {@link Boolean}- статус активности
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void editEmpl(Integer emplId, String firstName, String lastName, Integer emplDiv, Date birthdate, Boolean active, Double salary) {
        employeeDAO.editEmpl(emplId, firstName, lastName, emplDiv, birthdate, active, salary);
    }

    /**
     * Cоздает отсотрированный список отделов, на первую позицию устанавливается
     * отдел соответствующий введеному параметру id сотрудника
     *
     * @param division {@link List<Division>} cписок отделов содержащий инф. отделов
     * @param employees объект класса {@link Employees} 
     * @return {@link LinkedHashMap}  список отделов
     */
    public LinkedHashMap<Integer, String> isMapDivCreate(List<Division> division, Employees employees) {
        LinkedHashMap<Integer, String> parameters = new LinkedHashMap<Integer, String>();
        for (Division c2 : division) {
            if (employees.getEmplDiv().intValue() == c2.getDivId().intValue()) {
                parameters.put(c2.getDivId(), c2.getNameDiv());
            }
        }
        for (Division c2 : division) {
            if (employees.getEmplDiv().intValue() != c2.getDivId().intValue()) {
                parameters.put(c2.getDivId(), c2.getNameDiv());
            }
        }
        return parameters;
    }

    /**
     * Создает список содержащий инф. отделов  типа LinkedHashMap 
     *
     * @param division {@link List} список содержащий инф. отделов
     * @return {@link LinkedHashMap} список отделов
     */
    public LinkedHashMap<Integer, String> isMapDivCreate(List<Division> division) {
        LinkedHashMap<Integer, String> parameters = new LinkedHashMap<Integer, String>();
        for (Division divMap : division) {
            parameters.put(divMap.getDivId(), divMap.getNameDiv());
        }

        return parameters;
    }

    /**
     * Проверяет имя отдела на совпадение с именами других отделов
     *
     * @param divName {@link String} - имя отдела, которое нужно проверить
     * @return boolean - <b>true</b> - если имя отдела не совпадает с другими отделами,
     * <br/> иначе - <b>false</b>
     */
    public Boolean isValidDivName(String divName) {
       return employeeDAO.isValidDivName(divName);
//        List<Division> division = getAllDivision();
//        for (Division c : division) {
//            if (divName.equals(stringFrom.getStringName(c.getNameDiv()))) {
//                return false;
//            }
//        }
//        return true;
    }

    /**
     * Проверяет имя отдела на совпадение с именами других отделов,<br/>
     * исключая отдел, с переданным ID
     *
     * @param divName {@link String} - имя отдела, которое нужно проверить
     * @param divId int - ID отдела, который не учитывается при проверке
     *
     * @return boolean - <b>true</b> - если имя отдела не совпадает с другими
     * отделами,<br/> иначе - <b>false</b>
     */
    public boolean isValidDivName(String divName, int divId) {
         return employeeDAO.isValidDivName(divName, divId);
//        List<Division> division = getAllDivision();
//        for (Division c : division) {
//            if (divId != c.getDivId().intValue() && divName.equals(stringFrom.getStringName(c.getNameDiv()))) {
//                return false;
//            }
//        }
//        return true;
    }

    /**
     * Извлекает из БД количество строк в таблице сотрудников
     * @return <b> true </b> если количество сотрудников меньше 5000, иначе <b>flase</b>
     * больше
     */
    public Boolean getMaxEmplCounter() {
        return employeeDAO.getMaxEmplCounter();
    }
     /**
     * Извлекает из БД номер отдела с максимальным значением
     * @return <code> int </code> id последнего созданного отдела
     */
    public int getMaxDivId(){
        return employeeDAO.getMaxDivId();
    }
     /**
     * Извлекает из БД номер сотрдудника с максимальным значением
     * @return <code> int </code> id последнего введенного сотрудника
     */
    public int getMaxEmplId(){
        return employeeDAO.getMaxEmplId();
    }
}
