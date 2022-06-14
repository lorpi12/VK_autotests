package web.testng_style;

import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.*;
import ru.lanit.at.Sql.PostgreSql;
import ru.lanit.at.pages.AddEmployeePage;
import ru.lanit.at.pages.AuthPage;
import ru.lanit.at.pages.EmployeePage;
import ru.lanit.at.pages.MainPage;
import ru.lanit.at.steps.web.WebCheckWebSteps;
import ru.lanit.at.steps.web.WindowWebSteps;
import web.MainTest;
import web.PathOnLogin;

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
        System.getProperties().load(ClassLoader.getSystemResourceAsStream("accounts.properties"));
        PathOnLogin user = PathOnLogin.hr;
        windowWebSteps.open(System.getProperty("site.url"));
        authPage.fillFieldLogin(user.getLogin())
                .fillFieldPassword(System.getProperty(user.getLogin()))
                .clickOnCheckBoxAdminRules()
                .searchOtpTokenShouldBeVisible()
                .fillFieldOtpToken(getAuthToken(user))
                .clickOnButtonSignIn();
        mainPage.clickOnLinkEmployees();
        employeePage.clickOnButtonAddNewEmployee();
    }

    @AfterMethod
    public void afterTest() {
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
                {"+79000000000"}
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
        addEmployeePage.fillCitizenship(citizenship);
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


}
