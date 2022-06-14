package ru.lanit.at.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ru.lanit.at.utils.web.annotations.Name;
import ru.lanit.at.utils.web.pagecontext.WebPage;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.$x;


@Name(value = "Добавить Сотрудник")
public class AddEmployeePage extends WebPage {

    @Name("Фамилия")
    private SelenideElement surname = $x("//input[@name='surname']");

    @Name("Имя")
    private SelenideElement name = $x("//input[@name='name']");

    @Name("Отчество")
    private SelenideElement patronymic = $x("//input[@name='patronymic']");

    @Name("Пол")
    private SelenideElement gender = $x("//select[@name='gender']");

    @Name("Сохранить")
    private SelenideElement buttonSave = $x("//input[@name='_save']");

    @Name("Фото")
    private SelenideElement uploadPhoto = $x("//input[@id='id_photo']");

    @Name("Календарь")
    private SelenideElement calendar = $x("//div[@id='calendarbox0']");

    @Name("Прибавить дату в календаре")
    private SelenideElement calendarUp = $x("//div[@id='calendarbox0']//a[@class='calendarnav-next']");

    @Name("Убавить дату в календаре")
    private SelenideElement calendarDown = $x("//div[@id='calendarbox0']//a[@class='calendarnav-previous']");

    @Name("Модуль календаря")
    private SelenideElement calendarModule = $x("//a[@id='calendarlink0']/span[@class='date-icon']");

    @Name("Дата приема на работу")
    private SelenideElement joiningDate = $x("//input[@id='id_joining_date']");

    @Name("Дата приема на работу \"Cегодня\"")
    private SelenideElement todayDate = $x("//div[@class='fieldBox field-joining_date']//a[text()='Сегодня']");

    @Name("Телефон")
    private SelenideElement phone = $x("//input[@id='id_phone']");


    @Step("заполнить поле \"Фамилия\" значением {text} ")
    public AddEmployeePage fillSurnameField(String text) {
        surname.sendKeys(text);
        return this;
    }

    @Step("заполнить поле \"Имя\" значением {text} ")
    public AddEmployeePage fillNameField(String text) {
        name.sendKeys(text);
        return this;
    }

    @Step("заполнить поле \"Отчество\" значением {text} ")
    public AddEmployeePage fillPatronymicField(String text) {
        patronymic.sendKeys(text);
        return this;
    }

    @Step("заполнить селект \"Пол\" значением {value} ")
    public AddEmployeePage fillSelectGender(String value) {
        gender.selectOptionContainingText(value);
        return this;
    }

    @Step("нажать на кнопку \"Сохранить\" ")
    public void clickButtonSave() {
        buttonSave.click();
    }

    @Step("загрузить фото, расположенное в {path} ")
    public void uploadFile(String path) {
        uploadPhoto.uploadFile(new File(path));
    }

    @Step("Получить имя файла")
    public String getNameFile() {
        return uploadPhoto.getAttribute("value");
    }

    @Step("Получить имя файла")
    public void clickCalendarModule() {
        calendarModule.click();
    }

    @Step("Заполнение модуля календаря")
    public void fillCalendar(Calendar data) throws ParseException {
        Locale rus = new Locale("ru", "RU");
        Calendar cal = Calendar.getInstance();
        do {
            String yearAndMonth = calendar.$x(".//caption").getText().toLowerCase(rus).trim();

            int year = Integer.parseInt(yearAndMonth.substring(yearAndMonth.indexOf(" ") + 1));
            String month = yearAndMonth.substring(0, yearAndMonth.indexOf(" ")).toLowerCase(rus);

            SimpleDateFormat sdf = new SimpleDateFormat("MMMM", rus);
            cal.setTime(sdf.parse(month));
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.DAY_OF_MONTH, data.get(Calendar.DAY_OF_MONTH));

            if (data.compareTo(cal) == 1) {
                calendarUp.click();
            } else if (data.compareTo(cal) == -1) calendarDown.click();
        }
        while (data.compareTo(cal) != 0);

        calendar.$x(".//a[text()='" + data.get(Calendar.DAY_OF_MONTH) + "']").click();
    }

    @Step("Очистить поле \"Дата приема на работу\" ")
    public void clearJoiningDate() {
        joiningDate.clear();
    }

    @Step("Заполнить поле \"Дата приема на работу\" ")
    public void fillJoiningDate(String data) {
        joiningDate.sendKeys(data);
    }

    @Step("Нажать на кнопку \"Cегодня\"")
    public void clickTodayDate() {
        todayDate.click();
    }

    @Step("Заполнить поле \"Телефон\"")
    public void fillPhone(String number) {
        phone.sendKeys(number);
    }

    @Step("Получить значение с поля \"Телефон\"")
    public String getPhone() {
        return phone.getAttribute("value");
    }


    @Step
    public String getJoiningDate() {
        return joiningDate.getAttribute("value");
    }

    @Step("Заполнить поле \"Телефон\"")
    public void fillPhone2r(String number) {
        phone.sendKeys(number);
    }

    @Step("Заполнить поле \"Телефон\"")
    public void fillPhone3(String number) {
        phone.sendKeys(number);
    }




}
