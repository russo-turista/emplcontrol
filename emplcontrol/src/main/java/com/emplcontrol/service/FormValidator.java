package com.emplcontrol.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.springframework.stereotype.Service;

/**
 *
 * Реализация валидации данных. 
 */
@Service
public class FormValidator {

    /**
     * Проверка даты рождения больше 1900 года
     *
     * @param birthdate {@link Date} дата рождения сотрудника
     * @return <b>true</b> если дата больше или равна 1900 года, иначе <b>false</b>
     */
    public boolean getYear(Date birthdate) {

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(birthdate);
        int birthYear = calendar.get(Calendar.YEAR);
        if (birthYear != 0) {
            if (birthYear >= 1900) {
                return true;
            }
        }
        return false;
    }

    /**
     * Переводим спец символы поиска в правильный формат для БД
     *
     * @param searchString {@link String} строка поиска
     * @return {@link String} - строку в необходимом формате
     */
    
    public String parseSearch(String searchString) {
        searchString = searchString.trim();
        StringBuilder searchStringBuld = new StringBuilder();
        if (searchString.length()== 0) {
            return "%";
        } else {
            char[] chars = searchString.toCharArray();
            int j = 0;
            for (char c : chars) {
                ++j;
                if (j == 1) {
                    c = Character.toUpperCase(c);
                } else {
                    c = Character.toLowerCase(c);
                }

                if (c == '?') {
                    searchStringBuld.append("_");
                } else {
                    if (c != '*') {
                         searchStringBuld.append(c);
                    } else {
                       searchStringBuld.append("%");
                        if (chars.length == j) {
                            return  searchStringBuld.toString();
                        }
                    }
                }
            }
            return searchStringBuld.toString();
        }
    }
}