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


public class TestPhoto extends MainWebClass {

    private final WindowWebSteps windowWebSteps = new WindowWebSteps(null);
    private final AuthPage authPage = new AuthPage();
    private final PasswordPage passPage = new PasswordPage();
    private final MainPage mainPage = new MainPage();
    private final PhotoPage photoPage = new PhotoPage();

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
        photoPage.deletePublicAlbum();
        Sleep.pauseSec(1);
    }

    @AfterClass
    public void afterClass() {
        windowWebSteps.closeDriver();
    }

    @Test
    public void Test() {
        mainPage.clickPhoto();
        createPrivateAlbum();
        Sleep.pauseSec(1);
        workWithPhoto();
        windowWebSteps.open(System.getProperty("site.url"));
        mainPage.clickPhoto();
        createPublicAlbum();
        mainPage.clickPhoto();
        photoPage.clickSelectPrivateAlbum();
        Sleep.pauseSec(1);
        photoPage.transferToPublicAlbum();
        photoPage.deleteAlbum();
    }

    public void createPrivateAlbum() {
        photoPage.clickCreateAlbum();
        photoPage.setTitleAlbum("АльбомПриват");
        photoPage.setPrivacyControl();
        photoPage.clickCreateAlbumButton();
    }

    public void createPublicAlbum() {
        photoPage.clickCreateAlbum();
        photoPage.setTitleAlbum("АльбомПаблик");
        photoPage.clickCreateAlbumButton();
    }

    public void workWithPhoto() {
        photoPage.addPhoto("src/test/resources/photoFile/kotik.jpg");
        photoPage.makeCoverAlbum();
        photoPage.addComment("Комментарий к фото");
        photoPage.markPeople();
    }


}
