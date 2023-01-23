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


public class TestDiscussion extends MainWebClass {

    private final WindowWebSteps windowWebSteps = new WindowWebSteps(null);
    private final AuthPage authPage = new AuthPage();
    private final PasswordPage passPage = new PasswordPage();
    private final MainPage mainPage = new MainPage();
    private final CommunityPage communityPage = new CommunityPage();
    private final GroupPage groupPage = new GroupPage();

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
        passPage.fillPasswdField(Crypto.decrypt(login.getPassword()))
                .clickContinueButton();
    }

    @AfterMethod
    public void afterTest() {
        groupPage.leaveInGroup();
    }

    @AfterClass
    public void afterClass() {
        windowWebSteps.closeDriver();
    }

    @Test
    public void Test() {
        mainPage.clickCommunities();
        createGroup();
        createDiscussion();
        pinDiscussion();
        workWithComment();
    }

    public void createGroup() {
        communityPage.clickNewGroup();
        communityPage.clickGroupTitleInterests();
        communityPage.setGroupTitle("Группа");
        communityPage.setGroupTheme();
        communityPage.clickButtonCreateGroup();
    }

    public void createDiscussion() {
        groupPage.clickCreateDiscussion();
        groupPage.setTitleDiscussion("Название обсуждения");
        groupPage.setTextDiscussion("Текст обсуждения");
        groupPage.clickButtonCreateDiscussion();
    }

    public void pinDiscussion() {
        groupPage.clickEditDiscussion();
        groupPage.clickPinDiscussion();
        groupPage.clickConfirmPin();
    }

    public void workWithComment() {
        groupPage.createComment("Первый коммент");
        Sleep.pauseSec(1);
        groupPage.createComment("Второй коммент");
        Sleep.pauseSec(1);
        groupPage.createComment("Третий коммент");
        Sleep.pauseSec(1);
        groupPage.editSecondComment("Изменил коммент");
        groupPage.deleteFirstComment();
    }

}
