package by.petko.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemeOptionRepository extends CrudRepository<ThemeOption, Integer> {
}
