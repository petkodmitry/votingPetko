package by.petko.dto;

import by.petko.persistence.Theme;
import by.petko.persistence.ThemeOption;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Statistics {
    private String themeName;
    private Map<String, Integer> themeOptionsMap = new HashMap<>();

    public Statistics(Theme theme) {
        this.themeName = theme.getThemeName();
        List<ThemeOption> themeOptions = theme.getOptions();
        for (ThemeOption themeOption : themeOptions) {
            this.themeOptionsMap.put(themeOption.getOptionName(), themeOption.getQuantity());
        }
    }

    public String getThemeName() {
        return themeName;
    }

    public Map<String, Integer> getThemeOptionsMap() {
        return themeOptionsMap;
    }
}
