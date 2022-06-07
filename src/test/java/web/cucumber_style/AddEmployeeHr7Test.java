package web.cucumber_style;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.lanit.at.steps.web.DebugWebSteps;
import ru.lanit.at.steps.web.WebActionWebSteps;
import ru.lanit.at.steps.web.WebCheckWebSteps;
import ru.lanit.at.steps.web.WindowWebSteps;
import ru.lanit.at.utils.web.pagecontext.PageManager;
import web.MainTest;
import web.PathOnLogin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddEmployeeHr7Test extends MainTest {

    /* Для осуществления поиска элементов в контексте конкретной страницы */
    private final PageManager pageManager = new PageManager();

    /* Классы шагов */
    private final DebugWebSteps debugWebSteps = new DebugWebSteps(pageManager);
    private final WindowWebSteps windowWebSteps = new WindowWebSteps(pageManager);
    private final WebActionWebSteps webActionWebSteps = new WebActionWebSteps(pageManager);
    private final WebCheckWebSteps webCheckWebSteps = new WebCheckWebSteps(pageManager);


    @BeforeMethod
    public void beforeTest() throws IOException {
        PathOnLogin user = PathOnLogin.hr;
        System.getProperties().load(ClassLoader.getSystemResourceAsStream("accounts.properties"));
        windowWebSteps.open(System.getProperty("site.url"));
        windowWebSteps.setPage("Страница авторизации");
        webActionWebSteps.fillTheField("Логин", user.getLogin());
        webActionWebSteps.fillTheField("Пароль", System.getProperty(user.getLogin()));
        webActionWebSteps.clickOnElement("Чекбокс \"Админские права\"");
        webCheckWebSteps.elementAppearOnThePage("Отп токен");
        webActionWebSteps.fillTheField("Отп токен", getAuthToken(user));
        webActionWebSteps.clickOnElement("Кнопка \"Войти\"");
        windowWebSteps.setPage("Главная страница");
        webActionWebSteps.clickOnElement("ссылка сотрудники");
        windowWebSteps.setPage("Сотрудники");
        webActionWebSteps.clickOnElement("ДОБАВИТЬ НОВЫЙ СОТРУДНИК+");
    }


    @DataProvider
    public Object[][] dataTest1() {
        return new Object[][]{
                {"surname1", "name1", "middleName1", "Male", "surnameAndName"}
        };
    }


    @Test(dataProvider = "dataTest1")
    public void test1(String surname, String name, String middleName, String gender, String key) {
        step1_1(surname);
        step1_2(name);
        step1_3(middleName);
        step1_4(gender);
        step1_5();
        step1_6(key);
        step1_7(surname, name, key);

    }

    private void step1_1(String surname) {
        debugWebSteps.stepNumber("1");
        windowWebSteps.setPage("Добавить Сотрудник");
        webActionWebSteps.fillTheField("Фамилия", surname);
    }

    private void step1_2(String name) {
        debugWebSteps.stepNumber("2");
        webActionWebSteps.fillTheField("Имя", name);
    }

    private void step1_3(String middleName) {
        debugWebSteps.stepNumber("3");
        webActionWebSteps.fillTheField("Отчество", middleName);
    }

    private void step1_4(String gender) {
        debugWebSteps.stepNumber("4");
        webActionWebSteps.fillTheSelect("Пол", gender);
    }

    private void step1_5() {
        debugWebSteps.stepNumber("5");
        webActionWebSteps.clickOnElement("Сохранить");
    }

    private void step1_6(String key) {
        debugWebSteps.stepNumber("6");
        windowWebSteps.setPage("Сотрудники");
        webActionWebSteps.saveTextField("линк успешное добавление сотрудника", key);
    }

    private void step1_7(String surname, String name, String key) {
        Assert.assertEquals(webActionWebSteps.getValueByKey(key), surname + " " + name, "Значения не равны");
    }


//    @DataProvider
//    public Object[][] dataTest2() {
//        return new Object[][]{
//                {"src/test/resources/photoFile/kotik.jpg", "kotik.jpg"}
//        };
//    }
//
//
//    @Test(dataProvider = "dataTest2")
//    public void test2(String path, String nameFile) {
//        step2_1(path);
//        step2_2(nameFile);
//    }
//
//    private void step2_1(String path) {
//        debugWebSteps.stepNumber("1");
//        windowWebSteps.setPage("Добавить Сотрудник");
//        webActionWebSteps.uploadFile("Фото", path);
//    }
//
//    private void step2_2(String nameFile) {
//        debugWebSteps.stepNumber("2");
//        webActionWebSteps.saveValueField("Фото", "namePhoto");
//        Assert.assertTrue(webActionWebSteps.getValueByKey("namePhoto").contains(nameFile));
//    }


}
