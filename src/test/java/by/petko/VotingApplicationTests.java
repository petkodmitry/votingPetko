package by.petko;

import by.petko.entities.ThemeOption;
import by.petko.entities.Theme;
import by.petko.repositories.ThemeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
//@WebAppConfiguration
//@ContextConfiguration/*(classes = {VotingApplication.class*//*, WebConfig.class, MyController.class*//*})*/
@AutoConfigureMockMvc
@SpringBootTest/*(properties = "application.properties")*//*(classes = {VotingApplication.class, WebConfig.class})*/
public class VotingApplicationTests {

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private TestRestTemplate restTemplate;


//    @Autowired
//    private WebApplicationContext wac;
//    @Autowired
//    private MyController controller;

    @Autowired
    private /*static*/ MockMvc mockMvc;

//    @Autowired
//    private WebApplicationContext wac;

    private static boolean isSetUpDone = false;
    private String testTheme1 = "testTheme1";
    private String testOption1 = "optionTest11";
    private String testOption2 = "optionTest12";
    private int testQuantity = 0;

    @Before
    @Transactional
    public void setUp() /*throws Exception*/ {
        if (isSetUpDone) return;

//        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
//        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
//        String content = "{\"theme\": \"testTheme1\", \"options\": [{\"optionName\": \"optionTest11\"," +
//                " \"quantity\": 0}, {\"optionName\": \"optionTest12\", \"quantity\": 0}]}";
//        mockMvc.perform(post("/themes").contentType(MediaType.APPLICATION_JSON).content(content));
//        content = "{\"theme\": \"testTheme2\", \"options\": [{\"optionName\": \"optionTest21\"," +
//                " \"quantity\": 7}, {\"optionName\": \"optionTest22\", \"quantity\": 7}]}";
//        mockMvc.perform(post("/themes").contentType(MediaType.APPLICATION_JSON).content(content));

        Theme theme = new Theme();
        theme.setThemeName(testTheme1);
        List<ThemeOption> options = new ArrayList<>();
        ThemeOption themeOption = new ThemeOption();
        themeOption.setOptionName(testOption1);
        themeOption.setQuantity(testQuantity);
        themeOption.setTheme(theme);
        options.add(themeOption);
        ThemeOption themeOption2 = new ThemeOption();
        themeOption2.setOptionName(testOption2);
        themeOption2.setQuantity(testQuantity);
        themeOption2.setTheme(theme);
        options.add(themeOption2);
        theme.setOptions(options);
//        themeRepository.save(theme);
//
//        theme = new Theme();
//        theme.setThemeName("testTheme2");
//        options = new ArrayList<>();
//        themeOption = new ThemeOption();
//        themeOption.setOptionName("optionTest21");
//        themeOption.setQuantity(2);
//        themeOption.setTheme(theme);
//        options.add(themeOption);
//        themeOption2 = new ThemeOption();
//        themeOption2.setOptionName("optionTest22");
//        themeOption2.setQuantity(2);
//        themeOption2.setTheme(theme);
//        options.add(themeOption2);
//        theme.setOptions(options);
//        themeRepository.save(theme);

        ResponseEntity<Theme> responseEntity = restTemplate.postForEntity("/themes", theme, Theme.class);
        Theme createdTheme = responseEntity.getBody();
        isSetUpDone = true;
    }

    @Test
    @Transactional
    public void getAll() throws Exception {
//        assertThat();
        mockMvc.perform(get("/themes")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].theme").exists())
                .andExpect(jsonPath("$[0].options").exists())
                .andExpect(jsonPath("$[0].options[0].optionName").exists())
                .andExpect(jsonPath("$[0].options[0].quantity").exists());
    }

    @Test
    @Transactional
    public void getOne() throws Exception {
        mockMvc.perform(get("/themes/1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.theme").value(testTheme1))
                .andExpect(jsonPath("$.options[0].optionName").value(testOption1))
                .andExpect(jsonPath("$.options[0].quantity").value(testQuantity))
                .andExpect(jsonPath("$.options[1].optionName").value(testOption2))
                .andExpect(jsonPath("$.options[1].quantity").value(testQuantity));
    }

    @Test
    @Transactional
    public void getNotExisting() throws Exception {
        mockMvc.perform(get("/themes/100")).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void createTheme() throws Exception {
//        Theme createTheme = new Theme();
//        createTheme.setThemeName("createTestTheme");
//        List<ThemeOption> options = new ArrayList<>();
//        ThemeOption option = new ThemeOption();
//        option.setOptionName("createTestOption1");
//        option.setQuantity(0);
//        option.setThemeName(createTheme);
//        options.add(option);
//        ThemeOption option2 = new ThemeOption();
//        option2.setOptionName("createTestOption2");
//        option2.setQuantity(0);
//        option2.setThemeName(createTheme);
//        options.add(option2);
//        createTheme.setOptions(options);
//        new MyController().addTheme(createTheme);

        String content = "{\"theme\": \"createTestTheme\", \"options\": [{\"optionName\": \"createTestOption1\"," +
                " \"quantity\": 0}, {\"optionName\": \"createTestOption2\", \"quantity\": 0}]}";
        mockMvc.perform(post("/themes").contentType(MediaType.APPLICATION_JSON)
                .content(content)).andExpect(status().isCreated());
    }

    @Test
    @Transactional
    public void createExistingTheme() throws Exception {
        String content = "{\"theme\": \"" + testTheme1 + "\", \"options\": [{\"optionName\": \"createTestOption1\"," +
                " \"quantity\": 0}, {\"optionName\": \"createTestOption2\", \"quantity\": 0}]}";
        mockMvc.perform(post("/themes").contentType(MediaType.APPLICATION_JSON)
                .content(content)).andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional
    public void startAndStopTheme() throws Exception {
        mockMvc.perform(put("/themes/1/start")).andExpect(status().isAccepted());
        mockMvc.perform(put("/themes/1/stop")).andExpect(status().isAccepted());
        mockMvc.perform(get("/themes/1")).andExpect(jsonPath("$.link").exists())
                .andExpect(jsonPath("$.startDate").exists())
                .andExpect(jsonPath("$.endDate").exists());
        mockMvc.perform(put("/themes/1/start")).andExpect(status().isMethodNotAllowed());
        mockMvc.perform(put("/themes/1/stop")).andExpect(status().isMethodNotAllowed());
    }

    @Test
    @Transactional
    public void startAndStopNotExistingTheme() throws Exception {
        mockMvc.perform(put("/themes/100/start")).andExpect(status().isNotFound());
        mockMvc.perform(put("/themes/100/stop")).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void getOpenedThemes() throws Exception {
        mockMvc.perform(get("/opened")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").doesNotExist())
                .andExpect(jsonPath("$[0].themeId").value(1))
                .andExpect(jsonPath("$[0].theme").value(testTheme1));
    }
}
