package com.emplcontrol.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Класс контроллера формы авторизации.
*/
@Controller
public class LoginController {

    /**
     * Mapping "/login", реализация стартовой страницы
     * @param model объект класса {@link ModelMap}
     * @return {@link String} - возвращает в качестве Вида MVC - login.vm
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String startPage(ModelMap model) {
        return "login";
    }

    /**
     * Mapping "/login", реализация формы авторизации
     *
     * @param model объект класса {@link ModelMap}
     * @return {@link String} - возвращает в качестве вида MVC - login.vm
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap model) {
        return "login";
    }

    /**
     * Mapping "/loginfailed", реализуется при ошибке вторизации
     *
     * @param model объект класса {@link ModelMap}
     * @return {@link String}- возвращает в качестве вида MVC - login.vm
     */
    @RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
    public String loginError(ModelMap model) {

        model.addAttribute("errorLogin", "true");
        return "login";

    }

    /**
     * Mapping "/logout", реализуется при повторе авторизации
     *
     * @param model объект класса {@link ModelMap}
     * 
     * @return {@link String}- возвращает в качестве вида MVC - login.vm
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(ModelMap model) {
        return "login";
    }
}