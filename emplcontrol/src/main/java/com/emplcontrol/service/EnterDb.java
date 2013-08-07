package com.emplcontrol.service;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Класс создающий список из 1000 сотрудников в БД
 */
@Service
@Transactional(readOnly = true)
public class EnterDb {

    /**
     * объект клaсса  {@link  EmployeeService}
     */
    @Inject
    private EmployeeService personService;
    /**
     * реализация интерфейса  {@link  Map} - где ключ случайное число, значение id
     * отдела
     */
    private Map<Integer, Integer> divMap;
    /**
     * массив {@link String}- содержит список фамилий
     */
     private String[] firstNameMale = {
        "\u0421\u0435\u043b\u0438\u043d\u0438\u0430\u0441", "\u0421\u0435\u043b\u0435\u0432\u043a\u0438\u0439", "\u0421\u0435\u043b\u0435\u0432\u043a", "\u0421\u0435\u043b\u0430\u0444\u0438\u0438\u043b", "\u0421\u0435\u043a\u0443\u043d\u0434", "\u0421\u0435\u0432\u0438\u0440", "\u0421\u0435\u0432\u0435\u0440\u0438\u043d", "\u0421\u0435\u0432\u0435\u0440\u0438\u0430\u043d", "\u0421\u0435\u0432\u0430\u0441\u0442\u0438\u0430\u043d", "\u0421\u0432\u044f\u0442\u043e\u0441\u043b\u0430\u0432", "\u0421\u0430\u0442\u0443\u0440\u043d\u0438\u043d", "\u041e\u0440", "\u041e\u043f\u0442\u0430\u0442", "\u041e\u043d\u0443\u0444\u0440\u0438\u0439", "\u041e\u043d\u0438\u0441\u0438\u0444\u043e\u0440", "\u041e\u043d\u0438\u0441\u0438\u043c", "\u041e\u043d\u0438\u0441\u0438\u0439", "\u041e\u043b\u0438\u043c\u043f\u0438\u043e\u0434\u043e\u0440", "\u041e\u043b\u0438\u043c\u043f\u0438\u0439", "\u041e\u043b\u0438\u043c\u043f\u0430\u043d", "\u041e\u043b\u0438\u043c\u043f", "\u041e\u043b\u0435\u0433", "\u041e\u043b\u0432\u0438\u0430\u043d", "\u041b\u0435\u043e\u043d\u0438\u0434", "\u041b\u0435\u043e\u043d", "\u041b\u0435\u0432\u043a\u0438\u0439", "\u041b\u0435\u0432\u0438\u0439", "\u041b\u0435\u0432\u0432\u0435\u0439", "\u041b\u0435\u0432", "\u041b\u0430\u0440\u0433\u0438\u0439", "\u041b\u0430\u043e\u0434\u0438\u043a\u0438\u0439", "\u041b\u0430\u043c\u043f\u0430\u0434", "\u041b\u0430\u0437\u0430\u0440\u044c", "\u0418\u043f\u0435\u0440\u0435\u0445\u0438\u0439", "\u0418\u043e\u0442\u0430\u043c", "\u0418\u043e\u0441\u0438\u0444", "\u0418\u043e\u0430\u0441\u0430\u0444", "\u0415\u0432\u0441\u0435\u0432\u0438\u0439", "\u0415\u0432\u0440\u0435\u0442", "\u0415\u0432\u043f\u043e\u0440", "\u0414\u0438\u0441\u0438\u0434\u0435\u0440\u0438\u0439", "\u0414\u0438\u0441\u0430\u043d", "\u0414\u0438\u043e\u0441\u043a\u043e\u0440\u0438\u0434", "\u0412\u0430\u0440\u0441\u0438", "\u0412\u0430\u0440\u0441\u0430\u0432\u0430", "\u0412\u0430\u0440\u0434\u0430\u043d\u0438\u0439", "\u0412\u0430\u0440\u0432\u0430\u0440", "\u0412\u0430\u0440\u0430\u0434\u0430\u0442", "\u0412\u0430\u043f\u0442\u043e\u0441", "\u0410\u0432\u0443\u043d\u0434\u0438\u0439", "\u0410\u0432\u0440\u0430\u043c\u0438\u0439", "\u0410\u0432\u0440\u0430\u0430\u043c\u0438\u0439", "\u0410\u0432\u0440\u0430\u0430\u043c", "\u0410\u0432\u043a\u0442", "\u0410\u0432\u043a\u0441\u0435\u043d\u0442\u0438\u0439", "\u0410\u0432\u0435\u043d\u0438\u0440", "\u0410\u0432\u0434\u043e\u043d", "\u0410\u0432\u0434\u0438\u0439", "\u0410\u0432\u0433\u0443\u0441\u0442\u0438\u043d", "\u0410\u0431\u043e", "\u0410\u0430\u0440\u043e\u043d"
    };
    /**
     * массив {@link String}- содержит список имен
     */
     private String[] lastNameMale = {
        "\u0425\u0430\u043d\u0438\u043d\u043e\u0432", "\u0425\u0430\u043d\u0438\u043b\u043e\u0432", "\u0425\u0430\u043c\u0438\u0434\u0443\u043b\u043b\u0438\u043d", "\u041f\u043e\u043f\u043e\u0432", "\u041f\u043e\u043c\u0435\u043b\u043e\u0432", "\u041f\u043e\u043b\u044b\u0433\u0430\u043b\u043e\u0432", "\u041c\u0438\u0442\u044c\u043a\u0438\u043d", "\u041c\u0438\u0442\u0440\u043e\u0445\u0438\u043d", "\u041b\u0435\u0441\u043a\u043e\u0432", "\u041b\u0435\u0440\u043e\u0435\u0432", "\u041b\u0435\u043e\u043d\u043e\u0432", "\u041b\u0435\u043a\u043e\u043c\u0446\u0435\u0432", "\u041b\u0435\u0433\u043a\u043e\u0434\u0438\u043c\u043e\u0432", "\u041a\u0430\u0443\u0444\u043c\u0430\u043d", "\u041a\u0430\u0442\u044c\u043a\u0438\u043d", "\u041a\u0430\u0442\u0435\u0440\u0438\u043d\u043e\u0447\u043a\u0438\u043d", "\u041a\u0430\u0442\u0430\u0435\u0432", "\u0418\u0433\u043e\u0448\u0435\u0432", "\u0418\u0433\u043e\u043d\u0438\u043d", "\u0418\u0433\u043d\u0430\u0442\u043e\u0432", "\u0418\u0433\u043d\u0430\u0442\u0435\u043d\u043a\u043e", "\u0418\u0432\u0430\u0448\u043a\u0438\u043d", "\u0415\u043d\u043e\u0442\u043e\u0432", "\u0415\u043b\u044c\u0446\u0438\u043d", "\u0415\u043b\u0438\u0441\u0435\u0435\u0432", "\u0415\u0436\u043e\u0432"
    };
    /**
     * массив {@link String}- содержит список отделов
     */
    private  String[] divisionList = {
        "\u0423\u043f\u0440\u0430\u0432\u043b\u0435\u043d\u0438\u044f", "\u0411\u0443\u0445\u0433\u0430\u043b\u0442\u0435\u0440\u0438\u044f", "\u0421\u0431\u044b\u0442\u0430", "\u041a\u0430\u0434\u0440\u043e\u0432\u044b\u0439", "\u041f\u0440\u043e\u0438\u0437\u0432\u043e\u0434\u0441\u0442\u0432\u0435\u043d\u043d\u044b\u0439"
    };
    /**
     * переменная типа <code> long </code>
     * принимает значение верхней границы диапазона дат соот. 2012-01-01
     */
    private  long d1 = 1325368800000l;
    /**
     * переменная типа <code> long </code>
     * принимает значение нижнего границы диапазона дат соот. 1900-01-01
     */
    private  long d2 = 2208995999000l;
    /**
     * Случайным образом выбирает фамилию из списка
     *
     * @return {@link String}- случайную фамилию
     */
    public String randomFirstName() {
        return firstNameMale[(int) (Math.random() * firstNameMale.length)];
    }

    /**
     * Случайным образом выбирает имя из списка
     *
     * @return {@link String}- случайное имя
     */
    public String randomLastName() {
        return lastNameMale[(int) (Math.random() * lastNameMale.length)];
    }

    /**
     * Создаем в БД список отделов
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void createDivList() {
        divMap = new HashMap<Integer, Integer>();
        for (int i = 0; i < divisionList.length; i++) {
            personService.addDiv(divisionList[i]);
            divMap.put(i, personService.getMaxDivId());
        }
    }

    /**
     * Выбирает случайным образом id отдела
     *
     * @return {@link Integer} номер отдела
     */
    public Integer randomDivisionId() {
        Set<Map.Entry<Integer, Integer>> set = divMap.entrySet();
        int i = (int) (Math.random() * divisionList.length);
        for (Map.Entry<Integer, Integer> div : set) {
            if (i == div.getKey()) {
                return div.getValue();
            }
        }
        return null;

    }

    /**
     * Создаем число соответсвующее зарплате сотрудника, устанавливаем
     * правильный формат.
     *
     * @return {@link Double}- зарплату сотрудника
     * @throws ParseException - если тип возвращаемого значение не  {@link Double}
     */
    public Double randomSalary() throws ParseException {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        String s = nf.format(Math.random() * 10000);
        double d = nf.parse(s).doubleValue();
        return d;
    }

    /**
     * Создаем случайную дату рождения сотрудника
     *
     * @return {@link Date} - дата сотрудника
     */
    public Date genDate(){
        Date date = new Date((long)(Math.random()*d2+d1));
        return date;
    }

    /**
     * Для содания сотрудника в БД
     *
     * @throws ParseException смотри описание Exception {@link #randomSalary()}
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void createEmpl() throws ParseException {
        personService.addEmpl(randomFirstName(), randomLastName(), randomDivisionId(), genDate(), randomSalary(), true);
    }

    /**
     * Создаем в БД список сотрудников и отделы
     *
     * @param counter
     * <code>int</code>- количество сотрудников которое необоходим создать в БД
     * @return <b>true<b> если заполнение данных в БД прошло успешно
     * @throws ParseException смотри описание Exception {@link #randomSalary()}
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public boolean createDb(int counter) throws ParseException {
        createDivList();
        for (int i = 0; i < counter; i++) {
            createEmpl();
        }
        return true;
    }
}
