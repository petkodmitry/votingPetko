package by.petko.repositories;

import by.petko.entities.ThemeOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends JpaRepository<ThemeOption, Integer> {
}
