package VK.web;

import com.codeborne.selenide.Selenide;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testng.Assert;
import org.testng.annotations.*;
import ru.lanit.at.crypto.Crypto;
import ru.lanit.at.db.config.DataBase;
import ru.lanit.at.db.datasource.LoginTable;
import ru.lanit.at.db.dto.Login;
import ru.lanit.at.pages.*;
import ru.lanit.at.steps.web.WindowWebSteps;
import ru.lanit.at.utils.Sleep;

import java.io.IOException;


public class test_EditProfile extends MainWebClass {

    private final WindowWebSteps windowWebSteps = new WindowWebSteps(null);
    private final AuthPage authPage = new AuthPage();
    private final PasswordPage passPage = new PasswordPage();
    private final MainPage mainPage = new MainPage();
    private final MySytePage mySytePage = new MySytePage();
    private final ProfilePage profilePage = new ProfilePage();

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
        profilePage.clearSmallInfo();
        profilePage.selectFamilyStatus(1);
        profilePage.clearCity();
        profilePage.clickSaveButton();
        profilePage.deletePhoto();
        Sleep.pauseSec(1);
    }

    @AfterClass
    public void afterClass() {
        windowWebSteps.closeDriver();
    }

    @Test
    public void Test() {
        mainPage.clickMySyte();
        mySytePage.clickEditProfile();
        fillEmptyInfo();
        Sleep.pauseSec(1);
        Selenide.refresh();
        testNewData();
        profilePage.uploadPhoto();
        profilePage.verifyPhotoUpload();
    }

    private void fillEmptyInfo() {
        Assert.assertEquals(profilePage.getSmallInfo(), "");
        profilePage.sendSmallInfo("Тестировщик");
        String familyStatus = profilePage.editFamilyStatus(2);
        Assert.assertEquals(familyStatus, "0");
        Assert.assertEquals(profilePage.getCity(), "");
        profilePage.sendCity("Москва");
        profilePage.clickSaveButton();
    }

    private void testNewData() {
        Assert.assertEquals(profilePage.getSmallInfo(), "Тестировщик");
        Assert.assertEquals(profilePage.getFamilyStatus(), "1");
        Assert.assertEquals(profilePage.getCity(), "Москва");
    }

}
