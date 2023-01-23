package ru.lanit.at.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ru.lanit.at.utils.web.pagecontext.WebPage;

import static com.codeborne.selenide.Selenide.$x;

public class AuthPage extends WebPage {

    private SelenideElement phoneField = $x("//input[@id='index_email']");

    private SelenideElement enterButton = $x("//button[@type='submit']");

    @Step("Заполнение номера телефона")
    public AuthPage fillPhoneField(String phone) {
        phoneField.sendKeys(phone);
        return this;
    }

    @Step("Нажатие по кнопке 'Войти'")
    public void clickEnterButton() {
        enterButton.click();
    }

}
