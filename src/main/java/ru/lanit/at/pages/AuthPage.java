package ru.lanit.at.pages;

import com.codeborne.selenide.SelenideElement;
import ru.lanit.at.utils.web.pagecontext.WebPage;

import static com.codeborne.selenide.Selenide.$x;

public class AuthPage extends WebPage {

    private SelenideElement phoneField = $x("//input[@id='index_email']");

    private SelenideElement enterButton=$x("//button[@type='submit']");

    public AuthPage fillPhoneField(String phone) {
        phoneField.sendKeys(phone);
        return this;
    }

    public void clickEnterButton() {
        enterButton.click();
    }

}
