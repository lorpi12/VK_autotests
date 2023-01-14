package VK.web;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testng.annotations.*;
import ru.lanit.at.crypto.Crypto;
import ru.lanit.at.db.config.DataBase;
import ru.lanit.at.db.datasource.LoginTable;
import ru.lanit.at.db.dto.Login;
import ru.lanit.at.pages.*;
import ru.lanit.at.steps.web.WindowWebSteps;
import ru.lanit.at.utils.Sleep;

import java.io.IOException;


public class test_Chat extends MainWebClass {

    private final WindowWebSteps windowWebSteps = new WindowWebSteps(null);
    private final AuthPage authPage = new AuthPage();
    private final PasswordPage passPage = new PasswordPage();
    private final MainPage mainPage = new MainPage();
    private final MessangerPage messangerPage = new MessangerPage();

    private Login login;

    @BeforeClass
    public void beforeClass() {
        ApplicationContext config = new AnnotationConfigApplicationContext(DataBase.class);
        JdbcTemplate jdbcTemplate = config.getBean("jdbcTemplate", JdbcTemplate.class);
        LoginTable loginTable = new LoginTable(jdbcTemplate);
        login = loginTable.selectLogin();
    }

    @BeforeMethod
    public void beforeTest() throws IOException {
        System.getProperties().load(ClassLoader.getSystemResourceAsStream("config/configuration.properties"));
        windowWebSteps.open(System.getProperty("site.url"));
        auth();
    }

    public void auth() {
        authPage.fillPhoneField(Crypto.decrypt(login.getLogin()))
                .clickEnterButton();
        passPage.fillPhoneField(Crypto.decrypt(login.getPassword()))
                .clickContinueButton();
    }

    @AfterMethod
    public void afterTest() {
        messangerPage.exitFromChat();
        Sleep.pauseSec(1);
    }

    @AfterClass
    public void afterClass() {
        windowWebSteps.closeDriver();
    }

    @Test
    public void Test() {
        mainPage.clickMessanger();
        createChat();
        editSettingsChat();
        workWithMessage();
    }

    public void createChat() {
        messangerPage.clickNewChat();
        messangerPage.setNameChat("Важно");
        messangerPage.clickCreateChatButton();
        Sleep.pauseSec(1);
    }

    public void editSettingsChat() {
        messangerPage.clickChatSettingsButton();
        messangerPage.setNameChatSettings("Ну очень важная беседа");
        messangerPage.clickAddNewMember();
        messangerPage.exitChatSettings();
    }

    public void workWithMessage() {
        messangerPage.setMessageArea("Это очень важная беседа, выходить!");
        messangerPage.editMessage("Это очень важная беседа, НЕ выходить!");
        messangerPage.pinMessage();
        messangerPage.setMessageArea("А нет, лучше в группу");
    }


}
