package ru.lanit.at.pages;

import com.codeborne.selenide.Condition;
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

    @Name("Календарь \"Дата приема на работу\"")
    private SelenideElement calendarJoiningDate = $x("//div[@id='calendarbox0']");

    @Name("Прибавить дату в календаре \"Дата приема на работу\"")
    private SelenideElement calendarUpJoiningDate = $x("//div[@id='calendarbox0']//a[@class='calendarnav-next']");

    @Name("Убавить дату в календаре \"Дата приема на работу\"")
    private SelenideElement calendarDownJoiningDate = $x("//div[@id='calendarbox0']//a[@class='calendarnav-previous']");

    @Name("Модуль календаря \"Дата приема на работу\"")
    private SelenideElement calendarModuleJoiningDate = $x("//a[@id='calendarlink0']/span[@class='date-icon']");

    @Name("Дата приема на работу")
    private SelenideElement joiningDate = $x("//input[@id='id_joining_date']");

    @Name("Дата приема на работу \"Cегодня\"")
    private SelenideElement todayDateJoiningDate = $x("//div[@class='fieldBox field-joining_date']//a[text()='Сегодня']");

    @Name("Телефон")
    private SelenideElement phone = $x("//input[@id='id_phone']");

    @Name("Календарь \"Дата рождения\"")
    private SelenideElement calendarBirthday = $x("//div[@id='calendarbox1']");

    @Name("Прибавить дату в календаре \"Дата рождения\"")
    private SelenideElement calendarUpBirthday = $x("//div[@id='calendarbox1']//a[@class='calendarnav-next']");

    @Name("Убавить дату в календаре \"Дата рождения\"")
    private SelenideElement calendarDownBirthday = $x("//div[@id='calendarbox1']//a[@class='calendarnav-previous']");

    @Name("Модуль календаря \"Дата рождения\"")
    private SelenideElement calendarModuleBirthday = $x("//a[@id='calendarlink1']/span[@class='date-icon']");

    @Name("Дата рождения")
    private SelenideElement birthday = $x("//input[@id='id_birth']");

    @Name("Дата рождения \"Cегодня\"")
    private SelenideElement todayDateBirthday = $x("//div[@class='fieldBox field-birth']//a[text()='Сегодня']");

    @Name("Поле \"Гражданство\" кнопка \"Изменить выбранный объект типа\"")
    private SelenideElement citizenshipEditSelectedObject = $x("//a[@id='change_id_citizenship']");

    @Name("Поле \"Гражданство\" кнопка \"Удалить выбранный объект типа\"")
    private SelenideElement citizenshipDeleteSelectedObject = $x("//a[@id='delete_id_citizenship']");

    @Name("Поле \"Гражданство\"")
    private SelenideElement citizenship = $x("//select[@id='id_citizenship']");


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

    @Step("Нажать на модуль календаря \"Дата приема на работу\"")
    public void clickCalendarModuleJoiningDate() {
        calendarModuleJoiningDate.click();
    }


    @Step("Заполнение модуля календаря \"Дата приема на работу\"")
    public void fillCalendarJoiningDate(Calendar data) throws ParseException {
        Locale rus = new Locale("ru", "RU");
        Calendar cal = Calendar.getInstance();
        do {
            String yearAndMonth = calendarJoiningDate.$x(".//caption").getText().toLowerCase(rus).trim();

            int year = Integer.parseInt(yearAndMonth.substring(yearAndMonth.indexOf(" ") + 1));
            String month = yearAndMonth.substring(0, yearAndMonth.indexOf(" ")).toLowerCase(rus);

            SimpleDateFormat sdf = new SimpleDateFormat("MMMM", rus);
            cal.setTime(sdf.parse(month));
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.DAY_OF_MONTH, data.get(Calendar.DAY_OF_MONTH));

            if (data.compareTo(cal) == 1) {
                calendarUpJoiningDate.click();
            } else if (data.compareTo(cal) == -1) calendarDownJoiningDate.click();
        }
        while (data.compareTo(cal) != 0);

        calendarJoiningDate.$x(".//a[text()='" + data.get(Calendar.DAY_OF_MONTH) + "']").click();
    }

    @Step("Очистить поле \"Дата приема на работу\" ")
    public void clearJoiningDate() {
        joiningDate.clear();
    }

    @Step("Заполнить поле \"Дата приема на работу\" ")
    public void fillJoiningDate(String data) {
        joiningDate.sendKeys(data);
    }

    @Step("Нажать на кнопку \"Cегодня\" около поля \"Дата приема на работу\"")
    public void clickTodayDateJoiningDate() {
        todayDateJoiningDate.click();
    }

    @Step("Заполнить поле \"Дата приема на работу\"")
    public String getJoiningDate() {
        return joiningDate.getAttribute("value");
    }

    @Step("Заполнить поле \"Телефон\"")
    public void fillPhone(String number) {
        phone.sendKeys(number);
    }

    @Step("Получить значение с поля \"Телефон\"")
    public String getPhone() {
        return phone.getAttribute("value");
    }

    @Step("Нажать на модуль календаря \"Дата рождения\"")
    public void clickCalendarModuleBirthday() {
        calendarModuleBirthday.click();
    }

    @Step("Заполнение модуля календаря \"Дата рождения\"")
    public void fillCalendarBirthday(Calendar data) throws ParseException {
        Locale rus = new Locale("ru", "RU");
        Calendar cal = Calendar.getInstance();
        do {
            String yearAndMonth = calendarBirthday.$x(".//caption").getText().toLowerCase(rus).trim();

            int year = Integer.parseInt(yearAndMonth.substring(yearAndMonth.indexOf(" ") + 1));
            String month = yearAndMonth.substring(0, yearAndMonth.indexOf(" ")).toLowerCase(rus);

            SimpleDateFormat sdf = new SimpleDateFormat("MMMM", rus);
            cal.setTime(sdf.parse(month));
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.DAY_OF_MONTH, data.get(Calendar.DAY_OF_MONTH));

            if (data.compareTo(cal) == 1) {
                calendarUpBirthday.click();
            } else if (data.compareTo(cal) == -1) calendarDownBirthday.click();
        }
        while (data.compareTo(cal) != 0);

        calendarBirthday.$x(".//a[text()='" + data.get(Calendar.DAY_OF_MONTH) + "']").click();
    }

    @Step("Очистить поле \"Дата рождения\" ")
    public void clearBirthday() {
        birthday.clear();
    }

    @Step("Заполнить поле \"Дата рождения\" ")
    public void fillBirthday(String data) {
        birthday.sendKeys(data);
    }

    @Step("Нажать на кнопку \"Cегодня\" около поля \"Дата рождения\"")
    public void clickTodayDateBirthday() {
        todayDateBirthday.click();
    }

    @Step("Заполнить поле \"Дата рождения\"")
    public String getBirthday() {
        return birthday.getAttribute("value");
    }

    @Step("Заполнить поле \"Гражданство\"")
    public void fillCitizenship(int num) {
        citizenship.selectOptionByValue(String.valueOf(num));
    }

    @Step("Получить поле \"Гражданство\"")
    public String getCitizenship() {
        return citizenship.getValue();
    }

    @Step("Проверка активности кнопки \"Изменить выбранный объект типа\" поля \"Гражданство\"")
    public void checkActiveEditSelectedObject() {
        citizenshipEditSelectedObject.shouldBe(Condition.disabled);
    }

    @Step("Проверка не активности кнопки \"Изменить выбранный объект типа\" поля \"Гражданство\"")
    public void checkNotActiveEditSelectedObject() {
        citizenshipEditSelectedObject.shouldNotBe(Condition.attribute("href"));
    }

    @Step("Проверка активности кнопки \"Удалить выбранный объект типа\" поля \"Гражданство\"")
    public void checkActiveDeleteSelectedObject() {
        citizenshipDeleteSelectedObject.shouldBe(Condition.attribute("href"));
    }

    @Step("Проверка не активности кнопки \"Удалить выбранный объект типа\" поля \"Гражданство\"")
    public void checkNotActiveDeleteSelectedObject() {
        citizenshipDeleteSelectedObject.shouldNotBe(Condition.attribute("href"));
    }


}
