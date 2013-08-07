package com.emplcontrol.domain;

import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Service;

/**
 *
 * POJO класс для таблицы отделов
 */
@Service
public class Division {
    
    /**
     * переменная типа {@link Integer}- уникальный id отдела
     */

    private Integer divId;
    /**
     * переменная типа {@link String} имя отдела
     */
    @NotEmpty
    @Pattern(regexp = "[a-zA-Z\u0430-\u044F\u0410-\u042F\\s]*")
    private String nameDiv;

    /**
     * Get метод id отдела
     * @return {@link Integer} уникальный номер отдела
     */
    public Integer getDivId() {
        return divId;
    }

    /**
     * Set метод id отдела
     * @param divId  {@link Integer} уникальный номер отдела
     */
    public void setDivId(Integer divId) {
        this.divId = divId;
    }

    /**
     * Get метод название отдела
     * @return {@link String} название отдела
     */
    public String getNameDiv() {
        return nameDiv;
    }

    /**
     * Set метод название отдела
     * @param nameDiv {@link String} название отдела
     */
    public void setNameDiv(String nameDiv) {
        this.nameDiv = nameDiv.trim();
    }
}
