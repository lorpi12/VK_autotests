package ru.lanit.at.pages;

import com.codeborne.selenide.SelenideElement;
import ru.lanit.at.utils.web.pagecontext.WebPage;

import static com.codeborne.selenide.Selenide.$x;

public class PasswordPage extends WebPage {

    private SelenideElement passField = $x("//input[@name='password']");

    private SelenideElement continueButton = $x("//button[@type='submit']");

    public PasswordPage fillPhoneField(String pass) {
        passField.sendKeys(pass);
        return this;
    }

    public void clickContinueButton() {
        continueButton.click();
    }
}
