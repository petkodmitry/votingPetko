package by.petko.controllers;

import by.petko.entities.ThemeOption;
import by.petko.entities.Theme;
import by.petko.exceptions.NotAnOptionOfAThemeException;
import by.petko.exceptions.NotFoundException;
import by.petko.exceptions.NotAllowedException;
import by.petko.exceptions.ThemeExistsException;
import by.petko.repositories.OptionRepository;
import by.petko.repositories.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class MyController {

    @Autowired
    private ThemeRepository themeRepository;
    @Autowired
    private OptionRepository optionRepository;

    /**
     * Finds all themes in DataBase and gives them to the user
     * @return the list of actually all themes
     */
    @RequestMapping(value = "/themes", method = RequestMethod.GET)
    public Iterable<Theme> allThemesList() {
        return themeRepository.findAll();
    }

    /**
     * @param newTheme - Theme entity, converted form JSON format
     * @return newly created Theme with actual DataBase IDs
     */
    @RequestMapping(value = "/themes", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Theme addTheme(@RequestBody @Valid Theme newTheme) {
        Theme themeInDataBase = themeRepository.getByName(newTheme.getThemeName());
        if (themeInDataBase != null) throw new ThemeExistsException();
        for (ThemeOption option : newTheme.getOptions()) {
            option.setQuantity(0);
        }
        return themeRepository.save(newTheme);
    }

    /**
     * @param id - the ID of the Theme to be searched in DataBase
     * @return found Theme
     */
    @RequestMapping(value = "/themes/{id}", method = RequestMethod.GET)
    public Theme getTheme(@PathVariable("id") int id) {
        Theme result = themeRepository.findOne(id);
        if (result == null) throw new NotFoundException();
        return result;
    }

    /**
     * Starts voting process for the Theme (changes startDate option to current time value)
     * @param id - the ID of the Theme to be started
     */
    @RequestMapping(value = "/themes/{id}/start", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void startVoting(@PathVariable("id") int id) {
        Theme theme = themeRepository.findOne(id);
        if (theme == null) throw new NotFoundException();
        Date startDate = theme.getStartDate();
        Date endDate = theme.getEndDate();
        if (startDate != null || endDate != null ) throw new NotAllowedException();
        Link link = linkTo(methodOn(MyController.class).getTheme(id)).withSelfRel();
        theme.setLink(link.getHref());
        theme.setStartDate(new Date());
        themeRepository.save(theme);
    }

    /**
     * Stops voting process for the Theme (changes stopDate option to current time value)
     * @param id - the ID of the Theme to be stopped
     */
    @RequestMapping(value = "/themes/{id}/stop", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void stopVoting(@PathVariable("id") int id) {
        Theme theme = themeRepository.findOne(id);
        if (theme == null) throw new NotFoundException();
        Date startDate = theme.getStartDate();
        Date endDate = theme.getEndDate();
        if (startDate == null || endDate != null ) throw new NotAllowedException();
        theme.setEndDate(new Date());
        themeRepository.save(theme);
    }

    /**
     * Finds opened themes in DataBase and gives them to the user
     * @return the list of all opened themes
     */
    @RequestMapping(value = "/opened", method = RequestMethod.GET)
    public Set<Theme> getAllOpenedThemes() {
        return themeRepository.getOpenedThemes();
    }

    /**
     * @param id - the ID of the Theme to which the answer is given
     * @param optionId - the ID of the Option selected
     * @return the Theme with actual statuses of its Options
     */
    @RequestMapping(value = "/themes/{id}/{optionId}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public Theme voiceRegistration(@PathVariable("id") int id, @PathVariable("optionId") int optionId) {
        Theme theme = themeRepository.findOne(id);
        if (theme == null) throw new NotFoundException();
        ThemeOption themeOption = optionRepository.findOne(optionId);
        if (themeOption == null) throw new NotFoundException();
        if (theme.getEndDate() != null ||
                theme.getStartDate() == null ||
                theme.getStartDate().getTime() > new Date().getTime()){
            throw new NotAllowedException();
        }
        if (!theme.getOptions().contains(themeOption)) throw new NotAnOptionOfAThemeException();
        themeOption.setQuantity(themeOption.getQuantity() + 1);
        optionRepository.save(themeOption);
        return theme;
    }

    /**
     * Shows statistics of desired Theme
     * @param id - the ID of the Theme
     * @return a Map with one pair "Theme" - "[themeName]" and several pairs "[optionName]" - "[quantity]"
     */
    @RequestMapping(value = "/statistics/{id}", method = RequestMethod.GET)
    public Map<String, String> showStatistics(@PathVariable("id") int id) {
        Theme theme = themeRepository.findOne(id);
        if (theme == null) throw new NotFoundException();
        Map<String, String> result = new HashMap<>();
        result.put("Theme", theme.getThemeName());
        for (ThemeOption themeOption : theme.getOptions()) {
            result.put(themeOption.getOptionName(), themeOption.getQuantity().toString());
        }
        return result;
    }





    /**
     * Opens the JSP page with a new Theme add form
     * @return ModelAndView handler, to be resolved by a DispatcherServlet
     */
    @RequestMapping(value = "/addtheme", method = RequestMethod.GET)
    public ModelAndView openAddForm() {
        return new ModelAndView("/addTheme");
    }
}
