package com.emplcontrol.service;

import org.springframework.stereotype.Service;

/**
 *
 * Класс для обработки формата отображения имени и фамилии сотрудника
 */
@Service
public class StringForm {

    /**
     * Метод перовода в правильный регистр (Xxxxx) имени и фамили сотрудника
     *
     * @param stringTable {@link String} принимае строку которую необходимо
     * привести в нужный формат 
     * @return {@link String} строку в формате Xxxxx
     */
    public String getStringName(String stringTable) {
        StringBuilder stringTableBuld = new StringBuilder();
        char[] chars = stringTable.toCharArray();
        int i = 0;
        for (char c : chars) {
            ++i;
            if (i == 1) {
                c = Character.toUpperCase(c);
            } else {
                c = Character.toLowerCase(c);
            }
            stringTableBuld.append(c);
        }
        return stringTableBuld.toString();
    }
}
