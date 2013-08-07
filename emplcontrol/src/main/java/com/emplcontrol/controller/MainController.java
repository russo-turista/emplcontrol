package com.emplcontrol.controller;

import com.emplcontrol.domain.Division;
import com.emplcontrol.domain.Employees;
import com.emplcontrol.service.EmployeeService;
import com.emplcontrol.service.FormValidator;
import com.emplcontrol.service.StringForm;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.validation.Valid;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * Класс главного контроллера.
 */
@Service
@Controller
public class MainController {

    /**
     * переменная типа <code> boolean </code>
     * принимает значение <b> true </b> если пользователь авторизован и 
     * <b> false </b> если авторизация не прошла либо пользователь ROLE_ANONYMOUS
     */
    private boolean userRole = false;
    
    /**
     * переменная типа <code> boolean </code>
     * принимает значение <b>true</b> если год даты в строке birthdate меньше 1900
     */
    private boolean getYear = false;
    
    /**
     * переменная типа <code> boolean </code>
     * принимает значение <b>true</b> если результат поиска в списке сотрудников пуст
     * иначе <b>false</b>
     */
    private boolean emplNull = false;
    /**
     * переменная типа <code> boolean </code>
     * принимает значение  <b>false</b> если введенное имя отдела уже существует
     */
    private boolean divNameFlag = false;
     /**
     * переменная типа <code> boolean </code>
     * принимает значение  <b>false</b> если введенное имя отдела уже существует
     */
    private boolean divEditFlag = false;
    /**
     * переменная типа <code> boolean </code>
     * принимает значение  <b>true</b> если количество строк в таблице empl меньше 5000
     */
    private boolean count5000 = false;
    /**
     * переменная типа {@link String} 
     * принимает значение введеное в поле поиска firstName
     */
    private String firstNameSearch = "";
     /**
     * переменная типа {@link String} 
     * принимает значение введеное в поле поиска lastName
     */
    private String lastNameSearch = "";
    /**
     * объект класса  {@link FormValidator} 
     */
    @Inject
    private FormValidator formValidator;
    /**
     * объект класса  {@link  StringForm} 
     */
    @Inject
    private StringForm stringFrom;
     /**
     * объект клaсса  {@link  EmployeeService} 
     */
    @Inject
    private EmployeeService employeeService;

    /**
     * Проверка авторизации. Определение ролей доступа
     * пользователя
     *
     * @param model экземпляр интерфейса {@link Model}
     * @return {@link String} - представление вида <code>startpage</code>- главная страница проекта
     */
    @RequestMapping(value = "/startpage", method = RequestMethod.GET)
    public String getStartpage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> name = auth.getAuthorities();
        for (GrantedAuthority role : name) {
            if (role.getAuthority().toString().equals("ROLE_ADMIN")) {
                userRole = true;
                break;
            } else if (role.getAuthority().toString().equals("ROLE_ANONYMOUS")) {
                return "login";
            } else {
                userRole = false;
            }
        }
        Employees formEmpl = new Employees();
        model.addAttribute("formEmpl", formEmpl);
        model.addAttribute("userRole", userRole);
        model.addAttribute("emplNull", emplNull);
        model.addAttribute("count5000", count5000);
        emplNull = false;
        return "startpage";
    }

    /**
     * Отображения списка сотрудников
     *
     *@param model объект класса {@link Model}
     *  @return {@link String}- представление вида <code>emplpage</code>- таблица сотрудников
     */
    @RequestMapping(value = "/employees", method = RequestMethod.GET)
    public String getEmpl(Model model) {
        Employees formEmpl = new Employees();
        List<Employees> employees = employeeService.getAll();
        model.addAttribute("userRole", userRole);
        model.addAttribute("employees", employees);
        model.addAttribute("formEmpl", formEmpl);
        firstNameSearch = formValidator.parseSearch("");
        lastNameSearch = formValidator.parseSearch("");
        return "emplpage";
    }

    /**
     * Отображения списка сотрудников полученных в результате поиска
     *
     * @param formEmpl объект класса {@link Employees}
     * @return {@link ModelAndView}, если поиск завершился неудачно то возвращает на главную
     * страницу, если нет то список <code>emplpage</code>
     */
    @RequestMapping(value = "/employees", method = RequestMethod.POST)
    public ModelAndView getSerchRes(@ModelAttribute("formEmpl") Employees formEmpl) {
        ModelAndView modelAndView = new ModelAndView("emplpage");
        modelAndView.addObject("userRole", userRole);
        List<Employees> employee = employeeService.getSearch(formValidator.parseSearch(formEmpl.getSearchFirstName()), formValidator.parseSearch(formEmpl.getSearchLastName()));
        firstNameSearch = formValidator.parseSearch(formEmpl.getSearchFirstName());
        lastNameSearch = formValidator.parseSearch(formEmpl.getSearchLastName());
        modelAndView.addObject("employees", employee);
        if (employee.isEmpty()) {
            emplNull = true;
            return new ModelAndView("redirect:../startpage");
        }
        emplNull = false;
        modelAndView.addObject("search", formValidator.parseSearch(formEmpl.getSearchFirstName()) + "   " + formValidator.parseSearch(formEmpl.getSearchLastName()));
        return modelAndView;
    }

    /**
     * Отображает список сотрудников по поиску (сохраненный).
     *
     * @return {@link ModelAndView}- представление вида <code>emplpage</code>
     */
    @RequestMapping(value = "/emplsearch", method = RequestMethod.GET)
    public ModelAndView getSerchResEmpl() {
        ModelAndView modelAndView = new ModelAndView("emplpage");
        modelAndView.addObject("userRole", userRole);
        List<Employees> employees = employeeService.getSearch(firstNameSearch, lastNameSearch);
        modelAndView.addObject("employees", employees);
        return modelAndView;
    }

    /**
     * Вывод строки сотрудника
     *
     * @param emplId <code>int</code> номер сотрудника
     * @param model экземпляр интерфейса {@link Model}
     * @return {@link String}- emplpagefull прдставление страницы данных о сотруднике
     */
    @RequestMapping(value = "/employees/{emplId}", method = RequestMethod.GET)
    public String getEmployee(@PathVariable Integer emplId, Model model) {
        Employees formEmpl = new Employees();
        int divId = 0;
        model.addAttribute("formEmpl", formEmpl);
        Employees employees = employeeService.getEmpl(emplId);
        divId = employees.getEmplDiv();
        Division division = employeeService.getDiv(divId);
        model.addAttribute("employees", employees);
        model.addAttribute("division", division);
        return "emplpagefull";
    }

    /**
     *Реализует <code>registerCustomEditor</code> обработчик даты
     * @param binder объект класса {@link WebDataBinder}
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * Отображение и передачи формы данных для нового сотрудника
     *
     * @return {@link modelAndView} представление вида "addEditEmpl"
     */
    @RequestMapping(value = "/employees/addEmpl", method = RequestMethod.GET)
    public ModelAndView getAddEmployeeReq() {
        Employees formEmpl = new Employees();
        formEmpl.setActive(true);
        List<Division> division = employeeService.getAllDivision();
        ModelAndView modelAndView = new ModelAndView("addEditEmpl");
        modelAndView.addObject("formEmpl", formEmpl);
        modelAndView.addObject("division", employeeService.isMapDivCreate(division));
        modelAndView.addObject("mesYearFlag", getYear);
        modelAndView.addObject("addEditEmpl", true);
        getYear = false;
        return modelAndView;
    }

    /**
     * Принимает данные введенные с формы сотрудника помещая их БД
     *
     * @param formEmpl объект класса {@link Employees}
     * @param result реализует интерфейс {@link BindingResult} содержит результат валидации данных полученных с формы
     * сотрудников
     * @return {@link ModelAndView}, если кол. сотрудников больше 5000 представления вида <code>startpage</code>, 
     * если ошибка валидации возврат на форму ввода сотрудника
     */
    @RequestMapping(value = "/employees/addEmpl", method = RequestMethod.POST)
    public ModelAndView getAddEmployeeRes(@Valid @ModelAttribute("formEmpl") Employees formEmpl, BindingResult result) {
        if (!employeeService.getMaxEmplCounter()) {
            count5000 = true;
            return new ModelAndView("redirect:../startpage");
        }
        if (result.hasErrors()) {
            return getAddEmployeeReq();
        }
        if (!formValidator.getYear(formEmpl.getBirthdate())) {
            getYear = true;
            return getAddEmployeeReq();
        }
        getYear = false;
        employeeService.addEmpl(stringFrom.getStringName(formEmpl.getFirstName()), stringFrom.getStringName(formEmpl.getLastName()), formEmpl.getEmplDiv(), formEmpl.getBirthdate(), formEmpl.getSalary(), formEmpl.getActive());
        return new ModelAndView("redirect:../startpage");
    }

    /**
     * Вывод формы для изменения данных сотрудника.
     *
     * @param emplId <code>int</code> номер сотрудника
     * @return {@link modelAndView} с заполненной моделью.
     */
    @RequestMapping(value = "/employees/edit/{emplId}", method = RequestMethod.GET)
    public ModelAndView getEditEmployeeReq(@PathVariable int emplId) {
        Employees formEmpl = new Employees();
        Employees employees = employeeService.getEmpl(emplId);
        List<Division> division = employeeService.getAllDivision();
        formEmpl.setEmplId(emplId);
        formEmpl.setFirstName(employees.getFirstName());
        formEmpl.setLastName(employees.getLastName());
        formEmpl.setBirthdate(employees.getBirthdate());
        formEmpl.setSalary(employees.getSalary());
        formEmpl.setActive(employees.getActive());
        ModelAndView modelAndView = new ModelAndView("addEditEmpl");
        modelAndView.addObject("mesYearFlag", getYear);
        modelAndView.addObject("addEdiEmpl", false);
        getYear = false;
        modelAndView.addObject("formEmpl", formEmpl);
        modelAndView.addObject("division", employeeService.isMapDivCreate(division, employees));
        return modelAndView;
    }

    /**
     * Принимает и валидирует данные полученные с формы сотрудника
     *
     * @param emplId <code>int</code> номер сотрудника
     * @param formEmpl объект класса {@link Employees}
     * @param result  реализует интерфейс {@link BindingResult}, содержит результат валидации данных полученных с формы
     * сотрудников
     *  @return {@link modelAndView} если проверка валидации не проходит возвращает страницу формы
     * сотрудника, иначе переходит на список сотрудников
     */
    @RequestMapping(value = "/employees/edit/{emplId}", method = RequestMethod.POST)
    public ModelAndView getEditEmployeeRes(@PathVariable Integer emplId, @Valid @ModelAttribute("formEmpl") Employees formEmpl, BindingResult result) {
        if (result.hasErrors()) {
            return getEditEmployeeReq(emplId);
        }
        if (!formValidator.getYear(formEmpl.getBirthdate())) {
            getYear = true;
            return getEditEmployeeReq(emplId);
        }
        getYear = false;
        employeeService.editEmpl(emplId, stringFrom.getStringName(formEmpl.getFirstName()), stringFrom.getStringName(formEmpl.getLastName()), formEmpl.getEmplDiv(), formEmpl.getBirthdate(), formEmpl.getActive(), formEmpl.getSalary());
        return getSerchResEmpl();
    }

    /**
     * Выводит список отделов
     *
     * @param model экземпляр интерфейса {@link Model}
     * @return {@link String} представление <code>divisionlist</code>
     */
    @RequestMapping(value = "/division", method = RequestMethod.GET)
    public String getDivision(Model model) {
        List<Division> division = employeeService.getAllDivision();
        model.addAttribute("division", division);
        model.addAttribute("userRole", userRole);
        return "divisionlist";
    }

    /**
     * Ввод данных нового отдела в форму 
     *
     * @return {@link modelAndView} где представление addDiv
     */
    @RequestMapping(value = "/division/addDiv", method = RequestMethod.GET)
    public ModelAndView addDiv() {
        ModelAndView modelAndView = new ModelAndView("addDiv");
        Division formDiv = new Division();
        modelAndView.addObject("formDiv", formDiv);
        modelAndView.addObject("mesDiv", divNameFlag);
        divNameFlag = false;
        return modelAndView;
    }

    /**
     * Принимает заполненную форму с значением нового отдела. Валидирует и
     * вводит в базу данных.
     *
     * @param formDiv объек класса {@link Division}
     * @param result реализует интерфейс {@link BindingResult}, содержит результат валидации данных полученных с формы
     * отдела
     * @return {@link ModelAndView}, если данные не проходят валидацию то возвращает страницу формы
     * ввода отдела, иначе переходит на стартовую страницу
     */
    @RequestMapping(value = "/division/addDiv", method = RequestMethod.POST)
    public ModelAndView addDivRes(@Valid @ModelAttribute("formDiv") Division formDiv, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView("addDiv");
        if (result.hasErrors()) {
            modelAndView.addObject("finish", "Create division not complite");
            return modelAndView;
        }
        if (employeeService.isValidDivName(stringFrom.getStringName(formDiv.getNameDiv()))) {
            employeeService.addDiv(formDiv.getNameDiv());
            modelAndView.addObject("finish", "Create division complite");
            divNameFlag = false;
            return new ModelAndView("redirect:../startpage");
        } else {
            divNameFlag = true;
            return addDiv();
        }
    }

    /**
     *  Форма для изменение данных отдела
     *
     * @param divId <code>int</code> номер одела
     * @return {@link ModelAndView} содержит пердставление вида <code>editeDiv</code>
     */
    @RequestMapping(value = "/division/edit/{divId}", method = RequestMethod.GET)
    public ModelAndView getEditDivReq(@PathVariable int divId) {
        ModelAndView modelAndView = new ModelAndView("editeDiv");
        Division formDiv = new Division();
        Division division = employeeService.getDiv(divId);
        formDiv.setNameDiv(division.getNameDiv());
        modelAndView.addObject("mesDiv", divEditFlag);
        divEditFlag = false;
        modelAndView.addObject("formDiv", formDiv);
        modelAndView.addObject("division", division);
        return modelAndView;
    }

    /**
     * Принимает заполненую форму с значением отдела. Валидирует и вводит
     * в базу данных.
     *
     * @param divId <code>int</code>- номер отедла
     * @param formDiv объек класса  {@link Division}, заполненная форма отдела
     * @param result реализует интерфейс {@link BindingResult}, если не проходит валицация данных то перенаправляет на
     * форму отдела.
     * @return {@link ModelAndView} если данные не проходят валидацию то возвращает страницу формы
     * ввода отдела
     */
    @RequestMapping(value = "/division/edit/{divId}", method = RequestMethod.POST)
    public ModelAndView getEditDivRes(@PathVariable int divId, @Valid @ModelAttribute("formDiv") Division formDiv, BindingResult result) {

        ModelAndView modelAndView = new ModelAndView("editeDiv");
        if (result.hasErrors()) {
            modelAndView.addObject("finish", "Create division not complite");
            return getEditDivReq(divId);
        }
        if (employeeService.isValidDivName(stringFrom.getStringName(formDiv.getNameDiv()), divId)) {
            employeeService.editDiv(divId, formDiv.getNameDiv());
            divEditFlag = false;
            return new ModelAndView("redirect:../../../division/");
        } else {
            divEditFlag = true;
            return getEditDivReq(divId);
        }
    }
}
