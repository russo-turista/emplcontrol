
package com.emplcontrol.dao;

import com.emplcontrol.domain.Division;
import com.emplcontrol.domain.Employees;
import java.util.Date;
import java.util.List;

/**
 *
 * Интерфейс для DAO(data access object) отвечающиего за запись, модификацию сотрудников и отделов в
 * базе данных
 */
public interface EmployeeDAO {

    /**
     * Извлекает из БД полный список сотрудников
     *
     * @return {@link List}- список сотрудников
     */
    public List<Employees> getAll();

    /**
     * Извлекает из БД полный списока отделов
     *
     * @return {@link List} - список отделов
     */
    public List<Division> getAllDivision();

    /**
     * Извлекает из БД данные по соответствующему сотруднику
     *
     * @param emplId {@link Integer} индекс сотрудника
     * @return {@link Employees} - данные сотрудника
     */
    public Employees getEmpl(Integer emplId);

    /**
     * Извлекает из БД данные по определенному отделу
     *
     * @param divId {@link Integer} индекс отдела
     * @return {@link Division}- данные отдела
     */
    public Division getDiv(Integer divId);

    /**
     * Перезаписывает данные в БД по определенному отделу
     *
     * @param divId {@link Integer} индекс отдела
     * @param nameDiv {@link String} название отдела
     */
    public void editDiv(Integer divId, String nameDiv);

    /**
     * Реализует поиск сотрудников в БД
     *
     * @param firstName {@link String} запрашиваемое имя сотрудника
     * @param lastName {@link String} запрашиваемая фамилия сотрудника
     * @return {@link List}- возваращает список сотрдуников соответствующих
     * введемным параметрам
     */
    public List<Employees> getSearch(String firstName, String lastName);

    /**
     * Сохраняет в БД нового сотрудника
     *
     * @param firstName {@link String}- имя нового сотрудника
     * @param lastName {@link String}- фамилия нового сотрудника
     * @param emplDiv  {@link Integer}- номер отдела сотрудника
     * @param birthdate {@link Date}- день рождения сотрудника
     * @param salary {@link Double}- зарплата сотрудника
     * @param active {@link Boolean}- статус активности
     */
    public void addEmpl(String firstName, String lastName, Integer emplDiv, Date birthdate, Double salary, Boolean active);

    /**
     * Сохраняет в БД новый отдел
     *
     * @param nameDiv {@link Integer} имя нового отдела
     */
    public void addDiv(String nameDiv);

    /**
     * Вводит изменения по существующиму сотруднику
     *
     * @param emplId {@link Integer}- id сотрдуника
     * @param firstName {@link String}- имя сотрудника
     * @param lastName {@link String}- фамилия сотрудника
     * @param emplDiv  {@link Integer}- номер отдела сотрудника
     * @param birthdate {@link Date}- день рождения сотрудника
     * @param salary {@link Double}- зарплата сотрудника
     * @param active {@link Boolean}- статус активности
     */
    public void editEmpl(Integer emplId, String firstName, String lastName, Integer emplDiv, Date birthdate, Boolean active, Double salary);

    /**
     * Извлекает из БД количество строк в таблице сотрудников
     * @return <b> true </b> если количество сотрудников меньше 5000, иначе <b>flase</b>
     * больше
     */
    public Boolean getMaxEmplCounter();
    /**
     * Извлекает из БД номер отдела с максимальным значением
     * @return <code> int </code> id последнего созданного отдела
     */
    public int getMaxDivId();
     /**
     * Извлекает из БД номер сотрдудника с максимальным значением
     * @return <code> int </code> id последнего введенного сотрудника
     */
    public int getMaxEmplId();
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
    public boolean isValidDivName(String divName, int divId);
     
    /**
     * Проверяет имя отдела на совпадение с именами других отделов
     *
     * @param divName {@link String} - имя отдела, которое нужно проверить
     * @return boolean - <b>true</b> - если имя отдела не совпадает с другими отделами,
     * <br/> иначе - <b>false</b>
     */
    public Boolean isValidDivName(String divName);
}
