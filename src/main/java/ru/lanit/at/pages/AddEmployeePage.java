package ru.lanit.at.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ru.lanit.at.utils.web.annotations.Name;
import ru.lanit.at.utils.web.pagecontext.WebPage;

import java.io.File;

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


}
