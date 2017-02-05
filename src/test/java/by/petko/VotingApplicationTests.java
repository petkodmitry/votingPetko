package by.petko;

import by.petko.persistence.ThemeOption;
import by.petko.persistence.Theme;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {VotingApplication.class, TestConfig.class})
public class VotingApplicationTests {
    private static final String testTheme1 = "testTheme1";
    private static final String testOption1 = "optionTest11";
    private static final String testOption2 = "optionTest12";
    private static final String testTheme2 = "testTheme2";
    private static final String testOption3 = "optionTest21";
    private static final String testOption4 = "optionTest22";
    private static boolean isSetUpDone = false;

    @Autowired
    private TestRestTemplate restTemplate;

    private Theme newTheme(String themeName, String ... optionNames) {
        Theme result = new Theme();
        result.setThemeName(themeName);
        Set<ThemeOption> optionsList = new HashSet<>();
        for (String name : optionNames) {
            ThemeOption option = new ThemeOption();
            option.setOptionName(name);
            option.setQuantity(0);
            option.setTheme(result);
            optionsList.add(option);
        }
        result.setOptions(optionsList);
        return result;
    }

    @Before
    @Transactional
    public void setUp() {
        if (isSetUpDone) return;
        Theme theme = newTheme(testTheme1, testOption1, testOption2);
        restTemplate.postForEntity("/themes", theme, Theme.class);
        theme = newTheme(testTheme2, testOption3, testOption4);
        restTemplate.postForEntity("/themes", theme, Theme.class);
        theme = newTheme("testTheme3", "optionTest31");
        restTemplate.postForEntity("/themes", theme, Theme.class);
        restTemplate.exchange("/themes/1/start", HttpMethod.PUT, null, Theme.class);
        isSetUpDone = true;
    }

    @Test
    @Transactional
    public void getAll() {
        ResponseEntity<Theme[]> responseEntity = restTemplate.getForEntity("/themes", Theme[].class);
        Theme[] allThemes = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8);
        assertThat(allThemes.length).isGreaterThan(0);
        assertThat(allThemes[0].getOptions().size()).isGreaterThan(0);
    }

    @Test
    @Transactional
    public void getOne() {
        ResponseEntity<Theme> responseEntity = restTemplate.getForEntity("/themes/1", Theme.class);
        Theme theme = responseEntity.getBody();
        Set<ThemeOption> options = theme.getOptions();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8);
        assertThat(theme.getThemeName()).isEqualTo(testTheme1);
        assertThat(options.size()).isEqualTo(2);
        assertThat(options.stream().filter(themeOption -> themeOption.getOptionName().contains(testOption1)));
        assertThat(options.stream().filter(themeOption -> themeOption.getOptionName().contains(testOption2)));
    }

    @Test
    @Transactional
    public void getNotExisting() {
        ResponseEntity<Void> responseEntity = restTemplate.getForEntity("/themes/100", Void.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Transactional
    public void createTheme() {
        Theme theme = newTheme("createTestTheme",
                "createTestOption1", "createTestOption2", "createTestOption3", "createTestOption4");
        ResponseEntity<Theme> responseEntity = restTemplate.postForEntity("/themes", theme, Theme.class);
        assertThat(responseEntity.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @Transactional
    public void createExistingTheme() {
        Theme theme = newTheme(testTheme1);
        ResponseEntity<Void> responseEntity = restTemplate.postForEntity("/themes", theme, Void.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    @Transactional
    public void startAndStopTheme() {
        ResponseEntity<Theme> responseEntity = restTemplate.exchange("/themes/2/start",
                HttpMethod.PUT, null, Theme.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        responseEntity = restTemplate.exchange("/themes/2/stop", HttpMethod.PUT, null, Theme.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        responseEntity = restTemplate.getForEntity("/themes/2", Theme.class);
        Theme result = responseEntity.getBody();
        assertThat(result.getStartDate()).isNotNull();
        assertThat(result.getEndDate()).isNotNull();
        assertThat(result.getLink()).isNotNull();
        responseEntity = restTemplate.exchange("/themes/2/start", HttpMethod.PUT, null, Theme.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
        responseEntity = restTemplate.exchange("/themes/2/stop", HttpMethod.PUT, null, Theme.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Test
    @Transactional
    public void startAndStopNotExistingTheme() {
        ResponseEntity<Theme> responseEntity = restTemplate.exchange("/themes/100/start",
                HttpMethod.PUT, null, Theme.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        responseEntity = restTemplate.exchange("/themes/100/stop", HttpMethod.PUT, null, Theme.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Transactional
    public void getOpenedThemes() {
        ResponseEntity<Theme[]> responseEntity = restTemplate.getForEntity("/themes?status=opened", Theme[].class);
        Theme[] openedThemes = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8);
        assertThat(openedThemes.length).isEqualTo(1);
        assertThat(openedThemes[0].getThemeName()).isEqualTo(testTheme1);
        assertThat(openedThemes[0].getThemeId()).isEqualTo(1);
    }

    @Test
    @Transactional
    public void registerVoice() {
        ResponseEntity<Theme> responseEntity = restTemplate.exchange("/themes/1/1",
                HttpMethod.PUT, null, Theme.class);
        assertThat(responseEntity.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(responseEntity.getBody().getOptions().stream()
                .filter(themeOption -> themeOption.getQuantity().equals(1)));
    }

    @Test
    @Transactional
    public void registerVoice1() {
        ResponseEntity<Theme> responseEntity = restTemplate.exchange("/themes/100/1",
                HttpMethod.PUT, null, Theme.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        responseEntity = restTemplate.exchange("/themes/1/100",
                HttpMethod.PUT, null, Theme.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        responseEntity = restTemplate.exchange("/themes/2/3",
                HttpMethod.PUT, null, Theme.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
        responseEntity = restTemplate.exchange("/themes/3/5",
                HttpMethod.PUT, null, Theme.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
        responseEntity = restTemplate.exchange("/themes/1/5",
                HttpMethod.PUT, null, Theme.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Transactional
    public void getStatistics() {
        ResponseEntity<Theme> responseEntity = restTemplate.getForEntity("/themes/1/statistics", Theme.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Transactional
    public void getStatistics1() {
        ResponseEntity<Theme> responseEntity = restTemplate.getForEntity("/themes/100/statistics", Theme.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
