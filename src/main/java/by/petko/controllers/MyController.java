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

    @RequestMapping(value = "/themes", method = RequestMethod.GET)
    public List<Theme> allThemesList() {
        Iterable<Theme> result = themeRepository.findAll();
        List<Theme> resultList = new ArrayList<>();
        for (Theme theme : result) {
            resultList.add(theme);
        }
        return resultList;
    }

    @RequestMapping(value = "/themes", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Theme addTheme(@RequestBody @Valid Theme newTheme) {
        Theme themeInDataBase = themeRepository.getByName(newTheme.getThemeName());
        if (themeInDataBase != null) throw new ThemeExistsException();
        return themeRepository.save(newTheme);
    }

    @RequestMapping(value = "/themes/{id}", method = RequestMethod.GET)
    public Theme getTheme(@PathVariable("id") int id) {
        Theme result = themeRepository.findOne(id);
        if (result == null) throw new NotFoundException();
        return result;
    }

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

    @RequestMapping(value = "/opened", method = RequestMethod.GET)
    public Set<Theme> getAllOpenedThemes() {
        return themeRepository.getOpenedThemes();
    }

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

    @RequestMapping(value = "/statistics/{id}", method = RequestMethod.GET)
    public Map<String, String> showStatistic(@PathVariable("id") int id) {
        Theme theme = themeRepository.findOne(id);
        if (theme == null) throw new NotFoundException();
        Map<String, String> result = new HashMap<>();
        result.put("Theme", theme.getThemeName());
        for (ThemeOption themeOption : theme.getOptions()) {
            result.put(themeOption.getOptionName(), themeOption.getQuantity().toString());
        }
        return result;
    }

//    @RequestMapping(value = "/statistics/{id}", method = RequestMethod.GET)
//    public ResponseEntity<Theme> showStatistic(@PathVariable("id") int id) {
//        Theme theme = themesRepository.findOne(id);
//        if (theme == null) throw new NotFoundException();
//        MultiValueMap<String, String> headers = new HttpHeaders();
//        headers.put("123", Arrays.asList("example"));
//        headers.put("456", Arrays.asList("example2"));
//        ResponseEntity<Theme> responseEntity = new ResponseEntity<>(theme, headers, HttpStatus.OK);
//        return responseEntity;
//    }
}
