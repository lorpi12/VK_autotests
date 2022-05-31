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
        windowWebSteps.open("http://178.154.246.238:58082/");
        windowWebSteps.setPage("Страница авторизации");
        webActionWebSteps.fillTheField("Логин", readJsonFile(user, "username"));
        webActionWebSteps.fillTheField("Пароль", readJsonFile(user, "password"));
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
                {"surname", "name", "middleName", "Male", "surnameAndName"}
        };
    }


    @Test(dataProvider = "dataTest1")
    public void test1(String surname, String name, String middleName, String gender, String key) {
        step1(surname);
        step2(name);
        step3(middleName);
        step4(gender);
        step5();
        step6(key);
        step7(surname, name, key);

    }

    private void step1(String surname) {
        debugWebSteps.stepNumber("1");
        windowWebSteps.setPage("Добавить Сотрудник");
        webActionWebSteps.fillTheField("Фамилия", surname);
    }

    private void step2(String name) {
        debugWebSteps.stepNumber("2");
        webActionWebSteps.fillTheField("Имя", name);
    }

    private void step3(String middleName) {
        debugWebSteps.stepNumber("3");
        webActionWebSteps.fillTheField("Отчество", middleName);
    }

    private void step4(String gender) {
        debugWebSteps.stepNumber("4");
        webActionWebSteps.fillTheSelect("Пол", gender);
    }

    private void step5() {
        debugWebSteps.stepNumber("5");
        webActionWebSteps.clickOnElement("Сохранить");
    }

    private void step6(String key) {
        debugWebSteps.stepNumber("6");
        windowWebSteps.setPage("Сотрудники");
        webActionWebSteps.saveTextField("линк успешное добавление сотрудника", key);
    }

    private void step7(String surname, String name, String key) {
        Assert.assertEquals(webActionWebSteps.getValueByKey(key), surname + " " + name, "Значения не равны");
    }


}
