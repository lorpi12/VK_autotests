package ru.lanit.at.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ru.lanit.at.utils.web.pagecontext.WebPage;

import static com.codeborne.selenide.Selenide.$x;

public class PasswordPage extends WebPage {

    private SelenideElement passField = $x("//input[@name='password']");

    private SelenideElement continueButton = $x("//button[@type='submit']");

    @Step("Заполнить поле с паролем")
    public PasswordPage fillPasswdField(String pass) {
        passField.sendKeys(pass);
        return this;
    }

    @Step("Нажать на кнопку подтверждения")
    public void clickContinueButton() {
        continueButton.click();
    }
}
