package by.petko.repositories;

import by.petko.entities.Theme;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ThemeRepository extends CrudRepository<Theme, Integer> {

    /**
     * Searches a Theme by its themeName
     * @param theme - themeName to be searched in DataBase
     * @return found Theme
     */
    @Query("select T from Theme T where T.themeName = :theme")
    Theme getByName(@Param("theme") String theme);

    /**
     * Searches all opened Themes
     * @return the list of opened Themes
     */
    @Query("select T from Theme T where T.link is not null and T.endDate is null and T.startDate < current_timestamp")
    Set<Theme> getOpenedThemes();
}
