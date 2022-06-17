package ru.lanit.at.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ru.lanit.at.actions.WebChecks;
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

    @Name("Поле \"Гражданство\" кнопка \"Добавить еще один объект\"")
    private SelenideElement citizenshipAddObject = $x("//a[@id='add_id_citizenship']");

    @Name("Окно \"Добавить гражданство\" поле \"Name\"")
    private SelenideElement addCitizenshipName = $x("//input[@id='id_name']");

    @Name("Окно \"Добавить гражданство\" кнопка \"Сохранить\"")
    private SelenideElement addCitizenshipSave = $x("//input[@name='_save']");

    @Name("Окно \"Вы уверены?\" кнопка \"Yes, I`m sure\"")
    private SelenideElement deleteCitizenshipYesImSure = $x("//input[@value='Yes, I’m sure']");

    @Name("Окно \"Изменить Гражданство\" поле \"Name\"")
    private SelenideElement editCitizenshipName = $x("//input[@id='id_name']");

    @Name("Окно \"Изменить Гражданство\" кнопка \"Сохранить\"")
    private SelenideElement editCitizenshipSave = $x("//input[@name='_save']");

    @Name("Поле \"Корпоративная почта\"")
    private SelenideElement CorporationMail = $x("//input[@id='id_internal_email']");

    @Name("Кнопка \"Показать/Скрыть\" в блоке \"Квалификация\"")
    private SelenideElement showQualification = $x("//a[@id='fieldsetcollapser0']");

    @Name("Список чекбоксов \"Показатель квалификации\"")
    private SelenideElement indexOfQualification = $x("//ul[@id='id_qualification_skill']");

    @Name("Кнопка \"Добавить еще один объект\" в блоке \"Квалификация\"")
    private SelenideElement addObjectQualification = $x("//a[@id='add_id_qualification_skill']");

    @Name("Кнопка \"Показать/Скрыть\" в блоке \"Навыки\"")
    private SelenideElement showSkill = $x("//a[@id='fieldsetcollapser1']");

    @Name("Список чекбоксов \"Ключевой навык\"")
    private SelenideElement indexOfSkill = $x("//ul[@id='id_key_skill']");

    @Name("Кнопка \"Показать/Скрыть\" в блоке \"Сертификаты\"")
    private SelenideElement showCertificate = $x("//a[@id='fieldsetcollapser2']");

    @Name("Поле \"Курсы\" в блоке \"Сертификаты\"")
    private SelenideElement coursesСertificate = $x("//textarea[@id='id_courses']");

    @Name("Поле \"Сертификаты\" в блоке \"Сертификаты\"")
    private SelenideElement certificateСertificate = $x("//textarea[@id='id_certificates']");

    @Name("Кнопка \"Показать/Скрыть\" в блоке \"Статус сотрудника\"")
    private SelenideElement showStatusEmployee = $x("//a[@id='fieldsetcollapser3']");

    @Name("Список чекбоксов \"Статус сотрудников\"")
    private SelenideElement indexOfStatusEmployee = $x("//fieldset[@class='module aligned collapse']/div");

    @Step("Заполнить поле \"Фамилия\" значением {text} ")
    public AddEmployeePage fillSurnameField(String text) {
        surname.sendKeys(text);
        return this;
    }

    @Step("Заполнить поле \"Имя\" значением {text} ")
    public AddEmployeePage fillNameField(String text) {
        name.sendKeys(text);
        return this;
    }

    @Step("Заполнить поле \"Отчество\" значением {text} ")
    public AddEmployeePage fillPatronymicField(String text) {
        patronymic.sendKeys(text);
        return this;
    }

    @Step("Заполнить селект \"Пол\" значением {value} ")
    public AddEmployeePage fillSelectGender(String value) {
        gender.selectOptionContainingText(value);
        return this;
    }

    @Step("Нажать на кнопку \"Сохранить\" ")
    public void clickButtonSave() {
        buttonSave.click();
    }

    @Step("Загрузить фото, расположенное в {path} ")
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

    @Step("Заполнить поле \"Дата приема на работу\" значением {data}")
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

    @Step("Заполнить поле \"Телефон\" значением {number}")
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

    @Step("Заполнить поле \"Дата рождения\" значением {data} ")
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

    @Step("Выбрать в поле \"Гражданство\" по значению {num}")
    public void selectCitizenship(int num) {
        citizenship.selectOptionByValue(String.valueOf(num));
    }

    @Step("Выбрать в поле \"Гражданство\" значение по тексту {text}")
    public void selectCitizenshipByText(String text) {
        citizenship.selectOptionContainingText(text);
    }

    @Step("Получить поле \"Гражданство\"")
    public String getCitizenship() {
        return citizenship.getValue();
    }

    @Step("Проверка активности кнопки \"Изменить выбранный объект типа\" поля \"Гражданство\"")
    public void checkActiveEditSelectedObject() {
        citizenshipEditSelectedObject.shouldBe(Condition.attribute("href"));
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

    @Step("Нажать на кнопку \"Добавить еще один объект\" в поле \"Гражданство\"")
    public void clickCitizenshipAddObject() {
        citizenshipAddObject.click();
    }

    @Step("Нажать на кнопку \"Сохранить\" в окне \"Добавить гражданство\"")
    public void clickAddCitizenshipSave() {
        addCitizenshipSave.click();
    }

    @Step("Заполнить поле \"Name\" в окне \"Добавить гражданство\" значением {name}")
    public void fillAddCitizenshipName(String name) {
        addCitizenshipName.sendKeys(name);
    }

    @Step("Получить текст в поле \"Гражданство\"")
    public String getCitizenshipText() {
        return citizenship.getText();
    }

    @Step("Нажать кнопку \"Удалить выбранный объект типа\" поля \"Гражданство\"")
    public void clickCitizenshipDeleteSelectedObject() {
        citizenshipDeleteSelectedObject.click();
    }

    @Step("Нажать кнопку \"Изменить выбранный объект типа\" поля \"Гражданство\"")
    public void clickCitizenshipEditSelectedObject() {
        citizenshipEditSelectedObject.click();
    }

    @Step("Нажать кнопку \"Yes, I`m sure\" в окне \"Вы уверены?\" ")
    public void clickDeleteCitizenshipYesImSure() {
        deleteCitizenshipYesImSure.click();
    }

    @Step("получить значение из поля \"Name\" в окне \"Изменить Гражданство\"")
    public String getEditCitizenshipName() {
        return editCitizenshipName.getValue();
    }

    @Step("изменить поле \"Name\" в окне \"Изменить Гражданство\"")
    public void fillEditCitizenshipName() {
        String value = editCitizenshipName.getValue().substring(0, editCitizenshipName.getValue().length() - 2);
        editCitizenshipName.clear();
        editCitizenshipName.sendKeys(value);
    }

    @Step("нажать кнопку \"Сохранить\" в окне \"Изменить Гражданство\"")
    public void clickEditCitizenshipSave() {
        editCitizenshipSave.click();
    }

    @Step("Заполнить поле \"Корпоративная почта\"")
    public void fillCorporationMail(String mail) {
        CorporationMail.sendKeys(mail);
    }

    @Step("Получить значение из поля \"Корпоративная почта\"")
    public String getCorporationMail() {
        return CorporationMail.getValue();
    }

    @Step("Нажать на кнопку \"Показать/Скрыть\" в блоке \"Квалификация\"")
    public void clickShowQualification() {
        showQualification.click();
    }

    @Step("Выбирает {num} случайныx чек-боксов в блоке \"Квалификация\"")
    public void selectRandomCheckBoxQualification(int num) {
        for (int i = 0; i < num; i++) {
            indexOfQualification.$x("./li[" + (int) ((Math.random() * 46) + 1) + "]//input").click();
        }
    }

    @Step("Нажать на кнопку \"Добавить еще один объект типа\" в блоке \"Квалификация\"")
    public void clickAddObjectQualification() {
        addObjectQualification.click();
    }

    @Step("Нажать на кнопку \"Показать/Скрыть\" в блоке \"Навыки\"")
    public void clickShowSkill() {
        showSkill.click();
    }

    @Step("Выбирает {num} случайныx чек-боксов в блоке \"Навыки\"")
    public void selectRandomCheckBoxSkill(int num) {
        for (int i = 0; i < num; i++) {
            indexOfSkill.$x("./li[" + (int) ((Math.random() * 165) + 1) + "]//input").click();
        }
    }

    @Step("Нажать на кнопку \"Показать/Скрыть\" в блоке \"Сертификаты\"")
    public void clickShowCertificate() {
        showCertificate.click();
    }

    @Step("Заполнить текстом {text} поле \"Сертификаты\" в блоке \"Сертификаты\"")
    public void fillCertificateCertificate(String text) {
        certificateСertificate.sendKeys(text);
    }

    @Step("Заполнить текстом {text} поле \"Курсы\" в блоке \"Сертификаты\"")
    public void fillCoursesCertificate(String text) {
        coursesСertificate.sendKeys(text);
    }

    @Step("Проверяет, что элемента \"Сертификаты\" нет на странице")
    public void checkElementOnPageCertificate() {
        WebChecks.elementAbsentOnPage(certificateСertificate, 2);
    }

    @Step("Нажать на кнопку \"Показать/Скрыть\" в блоке \"Статус сотрудника\"")
    public void clickShowStatusEmployee() {
        showStatusEmployee.click();
    }

    @Step("Выбирает {num} случайныx чек-боксов в блоке \"Статус сотрудника\"")
    public void selectRandomCheckBoxStatusEmployee(int num) {
        for (int i = 0; i < num; i++) {
            indexOfStatusEmployee.$x("./div[" + (int) ((Math.random() * 3) + 1) + "]/input[@type='checkbox']").click();
        }
    }

    @Step("Проверяет, что чекбокса \"Работает\" нет на странице")
    public void checkElementOnPageStatusEmployee() {
        WebChecks.elementAbsentOnPage(indexOfStatusEmployee.$x("./div[1]/input[@type='checkbox']"), 2);
    }





}
