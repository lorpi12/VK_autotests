package web.testng_style;

import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.lanit.at.pages.AddEmployeePage;
import ru.lanit.at.pages.AuthPage;
import ru.lanit.at.pages.EmployeePage;
import ru.lanit.at.pages.MainPage;
import ru.lanit.at.steps.web.WindowWebSteps;
import web.MainTest;
import web.PathOnLogin;

import java.io.IOException;

public class AddEmployeeHr7Test extends MainTest {

    private final WindowWebSteps windowWebSteps = new WindowWebSteps(null);

    private final MainPage mainPage = new MainPage();
    private final AuthPage authPage = new AuthPage();
    private final EmployeePage employeePage = new EmployeePage();
    private final AddEmployeePage addEmployeePage = new AddEmployeePage();


    @DataProvider
    public Object[][] dataTest1() {
        return new Object[][]{
                {"surname", "name", "middleName", "Male", "surnameAndName"}
        };
    }

    @BeforeMethod
    public void beforeTest() throws IOException {
        PathOnLogin user = PathOnLogin.hr;
        windowWebSteps.open("http://178.154.246.238:58082/");
        authPage.fillFieldLogin(readJsonFile(user, "username"))
                .fillFieldPassword(readJsonFile(user, "password"))
                .clickOnCheckBoxAdminRules()
                .searchOtpTokenShouldBeVisible()
                .fillFieldOtpToken(getAuthToken(user))
                .clickOnButtonSignIn();
        mainPage.clickOnLinkEmployees();
        employeePage.clickOnButtonAddNewEmployee();
    }

    @Test(dataProvider = "dataTest1")
    public void Test1(String surname, String name, String middleName, String gender, String key) {
        step1(surname);
        step2(name);
        step3(middleName);
        step4(gender);
        step5();
        step6(surname, name);
    }

    @Step("Шаг №1")
    private void step1(String surname) {
        addEmployeePage.fillSurnameField(surname);
    }

    @Step("Шаг №2")
    private void step2(String name) {
        addEmployeePage.fillNameField(name);
    }

    @Step("Шаг №3")
    private void step3(String middleName) {
        addEmployeePage.fillPatronymicField(middleName);
    }

    @Step("Шаг №4")
    private void step4(String gender) {
        addEmployeePage.fillSelectGender(gender);
    }

    @Step("Шаг №5")
    private void step5() {
        addEmployeePage.clickButtonSave();
    }

    @Step("Шаг №6")
    private void step6(String surname, String name) {
        Assert.assertEquals(employeePage.getValueByLink(), surname + " " + name, "Значения не равны");
    }


}
