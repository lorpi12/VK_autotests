package web.testng_style;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.*;
import ru.lanit.at.Sql.PostgreSql;
import ru.lanit.at.actions.WebChecks;
import ru.lanit.at.pages.AddEmployeePage;
import ru.lanit.at.pages.AuthPage;
import ru.lanit.at.pages.EmployeePage;
import ru.lanit.at.pages.MainPage;
import ru.lanit.at.steps.web.WindowWebSteps;
import ru.lanit.at.utils.DataGenerator;
import ru.lanit.at.api.LoginAction.MainTest;
import ru.lanit.at.dictionaries.PathOnLogin;


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AddEmployeeHr7Test extends MainTest {

    private final WindowWebSteps windowWebSteps = new WindowWebSteps(null);

    private final MainPage mainPage = new MainPage();
    private final AuthPage authPage = new AuthPage();
    private final EmployeePage employeePage = new EmployeePage();
    private final AddEmployeePage addEmployeePage = new AddEmployeePage();


    @BeforeClass
    public void beforeClass() throws IOException {
        System.getProperties().load(ClassLoader.getSystemResourceAsStream("config/configuration.properties"));
        PathOnLogin user = PathOnLogin.HR;
        windowWebSteps.open(System.getProperty("site.url"));
        authPage.fillFieldLogin(user.getLogin())
                .fillFieldPassword(user.getPassword())
                .clickOnCheckBoxAdminRules()
                .searchOtpTokenShouldBeVisible()
                .fillFieldOtpToken(getAuthToken(user))
                .clickOnButtonSignIn();
    }

    @BeforeMethod
    public void beforeTest() {
        windowWebSteps.open(System.getProperty("site.url"));
        mainPage.clickOnLinkEmployees();
        employeePage.clickOnButtonAddNewEmployee();
    }

    @AfterClass
    public void afterClass() {
        windowWebSteps.closeDriver();
    }

    @DataProvider
    public Object[][] dataTest1() {
        return new Object[][]{
                {"surname3", "name3", "middleName3", "Male", "surnameAndName"}
        };
    }

    @Test(dataProvider = "dataTest1")
    public void Test1(String surname, String name, String middleName, String gender, String key) throws SQLException {
        step1_1(surname);
        step1_2(name);
        step1_3(middleName);
        step1_4(gender);
        step1_5();
        step1_6(surname, name);
        step1_7(name, surname, middleName, gender);
    }

    @Step("Шаг №1")
    private void step1_1(String surname) {
        addEmployeePage.fillSurnameField(surname);
    }

    @Step("Шаг №2")
    private void step1_2(String name) {
        addEmployeePage.fillNameField(name);
    }

    @Step("Шаг №3")
    private void step1_3(String middleName) {
        addEmployeePage.fillPatronymicField(middleName);
    }

    @Step("Шаг №4")
    private void step1_4(String gender) {
        addEmployeePage.fillSelectGender(gender);
    }

    @Step("Шаг №5")
    private void step1_5() {
        addEmployeePage.clickButtonSave();
    }

    @Step("Шаг №6")
    private void step1_6(String surname, String name) {
        Assert.assertEquals(employeePage.getValueByLink(), surname + " " + name, "Значения не равны");
    }

    @Step("Шаг №7")
    private void step1_7(String name, String surname, String middleName, String gender) throws SQLException {
        ResultSet resultSet = PostgreSql.getInstance().selectTableSql("SELECT name,surname,patronymic,gender from core_employee ce where ce.name ='" + name + "' and ce.surname ='" + surname +
                "' and ce.patronymic ='" + middleName + "'");
        Assert.assertTrue(resultSet.next());
        Assert.assertEquals(resultSet.getString("name"), name);
        Assert.assertEquals(resultSet.getString("surname"), surname);
        Assert.assertEquals(resultSet.getString("patronymic"), middleName);
        Assert.assertEquals(resultSet.getString("gender"), "м");
    }

    @DataProvider
    public Object[][] dataTest2() {
        return new Object[][]{
                {"src/test/resources/photoFile/kotik.jpg", "kotik.jpg"}
        };
    }

    @Test(dataProvider = "dataTest2")
    public void Test2(String path, String nameFile) {
        step2_1(path);
        step2_2(nameFile);
    }

    @Step("Шаг №1")
    private void step2_1(String path) {
        addEmployeePage.uploadFile(path);
    }

    @Step("Шаг №2")
    private void step2_2(String nameFile) {
        Assert.assertTrue(addEmployeePage.getNameFile().contains(nameFile));
    }

    @DataProvider
    public Object[][] dataTest3() {
        return new Object[][]{
                {new GregorianCalendar(2017, Calendar.JANUARY, 25)}
        };
    }

    @Test(dataProvider = "dataTest3")
    public void Test3(Calendar calendar) throws ParseException {
        step3_1();
        step3_2(calendar);
        step3_3(calendar);
    }

    @Step("Шаг №1")
    private void step3_1() {
        addEmployeePage.clickCalendarModuleJoiningDate();
    }

    @Step("Шаг №2")
    private void step3_2(Calendar calendar) throws ParseException {
        addEmployeePage.fillCalendarJoiningDate(calendar);
    }

    @Step("Шаг №3")
    private void step3_3(Calendar calendar) throws ParseException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        cal.setTime(sdf.parse(addEmployeePage.getJoiningDate()));
        Assert.assertEquals(cal.compareTo(calendar), 0);
    }

    @DataProvider
    public Object[][] dataTest4() {
        return new Object[][]{
                {"20.07.2016"}
        };
    }

    @Test(dataProvider = "dataTest4")
    public void Test4(String date) {
        step4_1();
        step4_2(date);


    }

    @Step("Шаг №1")
    private void step4_1() {
        addEmployeePage.clearJoiningDate();
    }

    @Step("Шаг №2")
    private void step4_2(String date) {
        addEmployeePage.fillJoiningDate(date);
        Assert.assertEquals(addEmployeePage.getJoiningDate(), date);
    }

    @DataProvider
    public Object[][] dataTest5() {
        return new Object[][]{
                {new Date()}
        };
    }

    @Test(dataProvider = "dataTest5")
    public void Test5(Date date) {
        step5_1();
        step5_2();
        step5_3(date);


    }

    @Step("Шаг №1")
    private void step5_1() {
        addEmployeePage.clearJoiningDate();
    }

    @Step("Шаг №2")
    private void step5_2() {
        addEmployeePage.clickTodayDateJoiningDate();
    }

    @Step("Шаг №3")
    private void step5_3(Date date) {
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy");
        String dateNow = formatForDateNow.format(date);
        Assert.assertEquals(addEmployeePage.getJoiningDate(), dateNow);
    }

    @DataProvider
    public Object[][] dataTest6() {
        return new Object[][]{
                {DataGenerator.generateValueByMask("+79DDDDDDDDD")}
        };
    }

    @Test(dataProvider = "dataTest6")
    public void Test6(String number) {
        step6_1(number);
    }

    @Step("Шаг №1")
    private void step6_1(String number) {
        addEmployeePage.fillPhone(number);
        Assert.assertEquals(addEmployeePage.getPhone(), number);
    }

    @Test(dataProvider = "dataTest3")
    public void Test7(Calendar calendar) throws ParseException {
        step7_1();
        step7_2(calendar);
        step7_3(calendar);
    }

    @Step("Шаг №1")
    private void step7_1() {
        addEmployeePage.clickCalendarModuleBirthday();
    }

    @Step("Шаг №2")
    private void step7_2(Calendar calendar) throws ParseException {
        addEmployeePage.fillCalendarBirthday(calendar);
    }

    @Step("Шаг №3")
    private void step7_3(Calendar calendar) throws ParseException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        cal.setTime(sdf.parse(addEmployeePage.getBirthday()));
        Assert.assertEquals(cal.compareTo(calendar), 0);
    }

    @Test(dataProvider = "dataTest4")
    public void Test8(String date) {
        step8_1();
        step8_2(date);


    }

    @Step("Шаг №1")
    private void step8_1() {
        addEmployeePage.clearBirthday();
    }

    @Step("Шаг №2")
    private void step8_2(String date) {
        addEmployeePage.fillBirthday(date);
        Assert.assertEquals(addEmployeePage.getBirthday(), date);
    }

    @Test(dataProvider = "dataTest5")
    public void Test9(Date date) {
        step9_1();
        step9_2();
        step9_3(date);
    }

    @Step("Шаг №1")
    private void step9_1() {
        addEmployeePage.clearBirthday();
    }

    @Step("Шаг №2")
    private void step9_2() {
        addEmployeePage.clickTodayDateBirthday();
    }

    @Step("Шаг №3")
    private void step9_3(Date date) {
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy");
        String dateNow = formatForDateNow.format(date);
        Assert.assertEquals(addEmployeePage.getBirthday(), dateNow);
    }

    @DataProvider
    public Object[][] dataTest10() {
        return new Object[][]{
                {1}
        };
    }

    @Test(dataProvider = "dataTest10")
    public void Test10(int citizenship) {
        step10_1();
        step10_2();
        step10_3(citizenship);
        step10_4();
        step10_5();
    }

    @Step("Шаг №1")
    private void step10_1() {
        addEmployeePage.checkNotActiveEditSelectedObject();
    }

    @Step("Шаг №2")
    private void step10_2() {
        addEmployeePage.checkNotActiveDeleteSelectedObject();
    }

    @Step("Шаг №3")
    private void step10_3(int citizenship) {
        addEmployeePage.selectCitizenship(citizenship);
        Assert.assertEquals(addEmployeePage.getCitizenship(), String.valueOf(citizenship));
    }

    @Step("Шаг №4")
    private void step10_4() {
        addEmployeePage.checkActiveEditSelectedObject();
    }

    @Step("Шаг №5")
    private void step10_5() {
        addEmployeePage.checkActiveDeleteSelectedObject();
    }

    @DataProvider
    public Object[][] dataTest11() {
        return new Object[][]{
                {"qwe123"}
        };
    }

    @Test(dataProvider = "dataTest11")
    public void Test11(String name) {
        step11_1();
        step11_2();
        step11_3(name);
        step11_4();
        step11_5(name);
    }

    @Step("Шаг №1")
    private void step11_1() {
        addEmployeePage.clickCitizenshipAddObject();
    }

    @Step("Шаг №2")
    private void step11_2() {
        Selenide.switchTo().window(1);
        WebChecks.textVisibleOnPage("Добавить Гражданство", 2);
    }

    @Step("Шаг №3")
    private void step11_3(String name) {
        addEmployeePage.fillAddCitizenshipName(name);
    }

    @Step("Шаг №4")
    private void step11_4() {
        addEmployeePage.clickAddCitizenshipSave();
    }

    @Step("Шаг №5")
    private void step11_5(String name) {
        Selenide.switchTo().window(0);
        Assert.assertEquals(addEmployeePage.getCitizenshipText(), name);
    }

    @Test(dataProvider = "dataTest11")
    public void Test12(String name) {
        step12_1(name);
        step12_2();
        step12_3();
        step12_4(name);
        step12_5();
        step12_6(name);

    }

    @Step("Шаг №1")
    private void step12_1(String name) {
        addEmployeePage.selectCitizenshipByText(name);
        Assert.assertEquals(addEmployeePage.getCitizenshipText(), name);
    }

    @Step("Шаг №2")
    private void step12_2() {
        addEmployeePage.clickCitizenshipEditSelectedObject();
    }

    @Step("Шаг №3")
    private void step12_3() {
        Selenide.switchTo().window(1);
        WebChecks.textVisibleOnPage("Изменить Гражданство", 2);
    }

    @Step("Шаг №4")
    private void step12_4(String name) {
        Assert.assertEquals(addEmployeePage.getEditCitizenshipName(), name);
        addEmployeePage.fillEditCitizenshipName();

    }

    @Step("Шаг №5")
    private void step12_5() {
        addEmployeePage.clickEditCitizenshipSave();
    }

    @Step("Шаг №6")
    private void step12_6(String name) {
        Selenide.switchTo().window(0);
        Assert.assertEquals(addEmployeePage.getCitizenshipText(), name.substring(0, name.length() - 2));
    }

    @Test(dataProvider = "dataTest11")
    public void Test13(String name) {
        step13_1(name);
        step13_2();
        step13_3();
        step13_4();
        step13_5();
    }

    @Step("Шаг №1")
    private void step13_1(String name) {
        addEmployeePage.selectCitizenshipByText(name.substring(0, name.length() - 2));
    }

    @Step("Шаг №2")
    private void step13_2() {
        addEmployeePage.clickCitizenshipDeleteSelectedObject();
    }

    @Step("Шаг №3")
    private void step13_3() {
        Selenide.switchTo().window(1);
        WebChecks.textVisibleOnPage("Вы уверены?", 2);
    }

    @Step("Шаг №4")
    private void step13_4() {
        addEmployeePage.clickDeleteCitizenshipYesImSure();
    }

    @Step("Шаг №5")
    private void step13_5() {
        Selenide.switchTo().window(0);
        Assert.assertEquals(addEmployeePage.getCitizenship(), "");

    }

    @DataProvider
    public Object[][] dataTest14() {
        return new Object[][]{
                {DataGenerator.generateValueByMask("EEEEE@EEEEE.EEE")}
        };
    }

    @Test(dataProvider = "dataTest14")
    public void Test14(String mail) {
        step14_1(mail);
    }

    @Step("Шаг №1")
    private void step14_1(String mail) {
        addEmployeePage.fillCorporationMail(mail);
        Assert.assertEquals(addEmployeePage.getCorporationMail(), mail);
    }

    @Test
    public void Test14_2() {
        step14_2_1();
        step14_2_2();
        step14_2_3();
        step14_2_4();
    }

    @Step("Шаг №1")
    private void step14_2_1() {
        addEmployeePage.clickShowQualification();
    }

    @Step("Шаг №2")
    private void step14_2_2() {
        addEmployeePage.selectRandomCheckBoxQualification(3);
    }

    @Step("Шаг №3")
    private void step14_2_3() {
        addEmployeePage.clickShowQualification();
    }

    @Step("Шаг №4")
    private void step14_2_4() {
        WebChecks.textAbsentOnPage("Общие квалификации: Автоматизированное тестирование", 2);
    }

    @Test
    public void Test15() {
        step15_1();
        step15_2();
        step15_3();
    }

    @Step("Шаг №1")
    private void step15_1() {
        addEmployeePage.clickShowQualification();
    }

    @Step("Шаг №2")
    private void step15_2() {
        addEmployeePage.clickAddObjectQualification();
    }

    @Step("Шаг №3")
    private void step15_3() {
        Selenide.switchTo().window(1);
        WebChecks.textVisibleOnPage("Добавить Показатель квалификации", 2);
        Selenide.switchTo().window(0);
    }

    @Test
    public void Test16() {
        step16_1();
        step16_2();
        step16_3();
        step16_4();
    }

    @Step("Шаг №1")
    private void step16_1() {
        addEmployeePage.clickShowSkill();
    }

    @Step("Шаг №2")
    private void step16_2() {
        addEmployeePage.selectRandomCheckBoxSkill(3);
    }

    @Step("Шаг №3")
    private void step16_3() {
        addEmployeePage.clickShowSkill();
    }

    @Step("Шаг №4")
    private void step16_4() {
        WebChecks.textAbsentOnPage("Языки программирования: C# 5.0", 2);
    }

    @Test
    public void Test18() {
        step18_1();
        step18_2();
        step18_3();
        step18_4();
        step18_5();
    }

    @Step("Шаг №1")
    private void step18_1() {
        addEmployeePage.clickShowCertificate();
    }

    @Step("Шаг №2")
    private void step18_2() {
        addEmployeePage.fillCertificateCertificate("Сертификаты");
    }

    @Step("Шаг №3")
    private void step18_3() {
        addEmployeePage.fillCoursesCertificate("Курсы");
    }

    @Step("Шаг №4")
    private void step18_4() {
        addEmployeePage.clickShowCertificate();
    }

    @Step("Шаг №5")
    private void step18_5() {
        addEmployeePage.checkElementOnPageCertificate();
    }

    @Test
    public void Test19() {
        step19_1();
        step19_2();
        step19_3();
        step19_4();

    }

    @Step("Шаг №1")
    private void step19_1() {
        addEmployeePage.clickShowStatusEmployee();
    }

    @Step("Шаг №2")
    private void step19_2() {
        addEmployeePage.selectRandomCheckBoxStatusEmployee(2);
    }

    @Step("Шаг №3")
    private void step19_3() {
        addEmployeePage.clickShowStatusEmployee();
    }

    @Step("Шаг №4")
    private void step19_4() {
        addEmployeePage.checkElementOnPageStatusEmployee();
    }


}
