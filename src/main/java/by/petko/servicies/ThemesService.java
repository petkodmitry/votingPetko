package by.petko.servicies;

import by.petko.repositories.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThemesService {

    @Autowired
    private ThemeRepository themeRepository;

    /*@Transactional
    public void generateLink(Theme theme) {
        if (theme.getLink() != null) return;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            int a = random.nextInt(900) + 100;
            char c = (char)(random.nextInt(26) + 'a');
            buffer.append(a).append(c);
        }
        String result = buffer.toString();
//        String result = "111a222b333c";
        if (themesRepository.isLinkExists(result) != null){
            generateLink(theme);
        }
        else{
            theme.setLink(result);
            themesRepository.save(theme);
        }
    }*/
}
