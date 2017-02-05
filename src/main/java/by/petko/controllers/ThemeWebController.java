package by.petko.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ThemeWebController {
    /**
     * Opens the JSP page with a new Theme add form
     * @return ModelAndView handler, to be resolved by a DispatcherServlet
     */
    @GetMapping(value = "/addthemeform")
    public ModelAndView openAddForm() {
        return new ModelAndView("/addThemeForm");
    }
}
