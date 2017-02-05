package by.petko.controllers;

import by.petko.dto.Statistics;
import by.petko.persistence.ThemeOption;
import by.petko.persistence.Theme;
import by.petko.exceptions.OptionNotFoundException;
import by.petko.exceptions.ThemeNotFoundException;
import by.petko.exceptions.NotAllowedException;
import by.petko.exceptions.ThemeExistsException;
import by.petko.persistence.ThemeOptionRepository;
import by.petko.persistence.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class ThemeRestController {

    @Autowired
    private ThemeRepository themeRepository;
    @Autowired
    private ThemeOptionRepository optionRepository;

    /**
     * Finds themes (all or by their status) in DataBase and gives them to the user
     * @return the list of themes
     */
    @GetMapping(value = "/themes")
    public Iterable<Theme> allThemesList(
            @RequestParam(required = false) String status) {
        if ("opened".equals(status)) {
            return themeRepository.getOpenedThemes();
        } else {
            return themeRepository.findAll();
        }
    }

    /**
     * @param newTheme - Theme entity, converted form JSON format
     * @return newly created Theme with actual DataBase IDs
     */
    @PostMapping(value = "/themes")
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
    @GetMapping(value = "/themes/{id}")
    public Theme getTheme(@PathVariable("id") int id) {
        return findOneRequired(id);
    }

    /**
     * Starts voting process for the Theme (changes startDate option to current time value)
     * @param id - the ID of the Theme to be started
     */
    @PutMapping(value = "/themes/{id}/start")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void startVoting(@PathVariable("id") int id) {
        Theme theme = findOneRequired(id);
        if (!theme.canBeStarted()) throw new NotAllowedException();
        Link link = linkTo(methodOn(ThemeRestController.class).getTheme(id)).withSelfRel();
        theme.setLink(link.getHref());
        theme.setStartDate(new Date());
        themeRepository.save(theme);
    }

    /**
     * Stops voting process for the Theme (changes stopDate option to current time value)
     * @param id - the ID of the Theme to be stopped
     */
    @PutMapping(value = "/themes/{id}/stop")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void stopVoting(@PathVariable("id") int id) {
        Theme theme = findOneRequired(id);
        if (!theme.isStarted()) throw new NotAllowedException();
        theme.setEndDate(new Date());
        themeRepository.save(theme);
    }

    /**
     * @param id - the ID of the Theme to which the answer is given
     * @param optionId - the ID of the Option selected
     * @return the Theme with actual statuses of its Options
     */
    @PutMapping(value = "/themes/{id}/{optionId}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public Theme voiceRegistration(@PathVariable("id") int id, @PathVariable("optionId") int optionId) {
        Theme theme = findOneRequired(id);
        ThemeOption themeOption = optionRepository.findOne(optionId);
        if (themeOption == null) throw new OptionNotFoundException();
        if (!theme.isStarted()) throw new NotAllowedException();
        if (!theme.getOptions().contains(themeOption)) throw new OptionNotFoundException();
        themeOption.setQuantity(themeOption.getQuantity() + 1);
        optionRepository.save(themeOption);
        return theme;
    }

    /**
     * Shows statistics of desired Theme
     * @param id - the ID of the Theme
     * @return a Statistics object for the Theme
     */
    @GetMapping(value = "/themes/{id}/statistics")
    public Statistics showStatistics(@PathVariable("id") int id) {
        Theme theme = findOneRequired(id);
        return new Statistics(theme);
    }

    /**
     * @param id - the ID of the Theme
     * @return the Theme found in the DataBase by given ID or throws ThemeNotFoundException
     */
    private Theme findOneRequired(int id) {
        Theme theme = themeRepository.findOne(id);
        if (theme == null) throw new ThemeNotFoundException();
        return theme;
    }
}
