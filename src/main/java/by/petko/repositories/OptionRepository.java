package by.petko.repositories;

import by.petko.entities.ThemeOption;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends CrudRepository<ThemeOption, Integer> {
}
