package com.emplcontrol.domain;

import java.util.Date;
import javax.validation.constraints.*;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;
import org.springframework.stereotype.Service;

/**
 *
 * POJO класс для таблицы сотрудников
 */
@Service()
public class Employees {
    /**
     * переменная типа {@link Integer} уникальный id отдела
     */
    private Integer emplDiv;
    /**
     * переменная типа {@link Double} зарплата сотрудника
     */
    @NotNull
    @NumberFormat(style = Style.NUMBER, pattern = "###############.##")
    @Digits(integer = 15, fraction = 2)
    @Min(1)
    private Double salary;
    /**
     * переменная типа {@link String} фамилия сотрудника
     */
    @NotEmpty
    @Pattern(regexp = "[a-zA-Z\u0430-\u044F\u0410-\u042F]*")
    private String firstName;
    
    /**
     * переменная типа {@link String} имя сотрудника
     */
    @NotEmpty
    @Pattern(regexp = "[a-zA-Z\u0430-\u044F\u0410-\u042F]*")
    private String lastName;
    
    /**
     * переменная типа {@link Boolean} состояние сотрудника раб./нераб.
     */
    private Boolean active;
    
    /**
     * переменная типа {@link Date} день рождения сотрудника
     */
    @NotNull
    @Past
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date birthdate;
    
    /**
     * переменная типа {@link String} с формы поиска по фамилии сотрудника
     */
    private String searchFirstName;
    
     /**
     * переменная типа {@link String} с формы поиска по имени сотрудника
     */
    
    private String searchLastName;
    
    /**
     * переменная типа {@link Integer} уникальный id сотрудника
     */
    private Integer emplId;

    /**
     * Get метод id сотрудника
     * @return {@link Integer} уникальный номер сотрудника
     */
    public Integer getEmplId() {
        return emplId;
    }

   /**
     * Устанавливает значение- id сотрудника
     * @param emplId  {@link Integer} уникальный номер сотрудника
     */
    public void setEmplId(Integer emplId) {
        this.emplId = emplId;
    }

    /**
     * Возвращает значение id отдела к которому относ. сотрудник
     * @return {@link Integer} id отдела к которому относ. сотрудник
     */
    public Integer getEmplDiv() {
        return emplDiv;
    }

    /**
     * Устанавливает значение- id отдела к которому относ. сотрудник
     * @param emplDiv {@link Integer} id отдела к которому относ. сотрудник
     */
    public void setEmplDiv(Integer emplDiv) {
        this.emplDiv = emplDiv;
    }

    /**
     * Возвращает значение  зарплаты сотрудника
     * @return {@link Double} зарплата сотрудника
     */
    public Double getSalary() {
        return salary;
    }

    /**
     * Устанавливает значение-  зарплаты сотрудника
     * @param salary {@link Double}- зарплата сотрудника
     */
    public void setSalary(Double salary) {
        this.salary = salary;
    }

    /**
     * Возвращает значение фамилии сотрудника
     * @return {@link String} фамилия сотрудника
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Устанавливает значение- фамилии сотрудника
     * @param firstName {@link String} фамилия сотрудника
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName.trim();
    }

    /**
     * Возвращает значение имени сотрудника
     * @return {@link String} имя сотрудника
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Устанавливает значение- имя сотрудника
     * @param lastName {@link String} имя сотрудника
     */
    public void setLastName(String lastName) {
        this.lastName = lastName.trim();
    }

    /**
     * Возвращает значение статуса сотрудника
     * @return {@link Boolean} <b>true</b> если сотрудник работает,
     * иначе <b>false</b>
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * Устанавливает значение- статуса сотрудника
     * @param active {@link Boolean} статус акт. сотрудника
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * Возвращает значение деня рождения сотрудника
     * @return {@link Date} день рождения сотрудника
     */
    public Date getBirthdate() {
        return birthdate;
    }

    /**
     * Устанавливает значение- день рождения сотрудника
     * @param birthdate {@link Date}- день рождения сотрудника
     */
    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    /**
     * Возвращает значение введенного в поле поиска сотрудника по фамилии
     * @return {@link String}- значение введенного в поле поиска сотрудника по фамилии
     */
    public String getSearchFirstName() {
        return searchFirstName;
    }

    /**
     * Устанавливает значение- полученное с поля поиска сотрудника по фамилии
     * @param searchFirstName {@link String}- строка полученная с поля поиска сотрудника по фамилии
     */
    public void setSearchFirstName(String searchFirstName) {
        this.searchFirstName = searchFirstName.trim();
    }

    /**
     * Возвращает значение введенного в поле поиска сотрудника по имени
     * @return {@link String}- значение введенного в поле поиска сотрудника по имени
     */
    public String getSearchLastName() {
        return searchLastName;
    }

    /**
     * Устанавливает значение- полученное с поля поиска сотрудника по имени
     * @param searchLastName {@link String}- строка полученная с поля поиска сотрудника по имени
     */
    public void setSearchLastName(String searchLastName) {
        this.searchLastName = searchLastName.trim();
    }
}
