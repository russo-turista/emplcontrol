package com.emplcontrol.controller;

import com.emplcontrol.service.EnterDb;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
    
/**
 * Контроллер.Cоздания 1000 сотрудников.
 */
@Controller
public class CreateDBController {
    /** 
     * Экземпляр класса {@link EnterDb}
     */
    @Inject 
        private EnterDb enterDb;
        
        /**
         * медот контроллера Mapping "/createDb", создает 1000 сотрудников
         * @return {@link ModelAndView}  стартовой страницы
         * @throws ParseException  возможны при ошибчном вводе NumberFormat
         *  метода {@link EnterDb#randomSalary()}.      
         */
        @RequestMapping(value="/createdb", method = RequestMethod.GET)
	public ModelAndView  createDBcontrl() throws ParseException {
            enterDb.createDb(1000);
            return new ModelAndView("redirect:/startpage");
 
	}
}