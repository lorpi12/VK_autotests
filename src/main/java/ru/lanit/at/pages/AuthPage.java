package ru.lanit.at.pages;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ru.lanit.at.utils.web.annotations.Name;
import ru.lanit.at.utils.web.pagecontext.WebPage;

import static com.codeborne.selenide.Selenide.$x;

@Name(value = "Страница авторизации")
public class AuthPage extends WebPage {

    @Name("Логин")
    private final SelenideElement login = $x("//input[@name='username']");

    @Name("Пароль")
    private final SelenideElement password = $x("//input[@name='password']");

    @Name("Отп токен")
    private final SelenideElement otpToken = $x("//input[@name='otp_token']");

    @Name("Чекбокс \"Админские права\"")
    private final SelenideElement checkBoxAdminRules = $x("//input[@name='i_am_admin']");

    @Name("Кнопка \"Войти\"")
    private final SelenideElement buttonSignIn = $x("//input[@value='Войти']");


    @Step("Нажать на кнопку \"Войти\"")
    public void clickOnButtonSignIn() {
        buttonSignIn.click();
    }

    @Step("Нажать на чекбокс \"Админские права\"")
    public AuthPage clickOnCheckBoxAdminRules() {
        checkBoxAdminRules.click();
        return this;
    }

    @Step("На странице отображается поле \"Отп токен\"")
    public AuthPage searchOtpTokenShouldBeVisible() {
        otpToken.shouldBe(Condition.visible);
        return this;
    }

    @Step("Заполнить поле \"Логин\" значением {text}")
    public AuthPage fillFieldLogin(String text) {
        login.sendKeys(text);
        return this;
    }

    @Step("Заполнить поле \"Пароль\" значением {text}")
    public AuthPage fillFieldPassword(String text) {
        password.sendKeys(text);
        return this;
    }

    @Step("Заполнить поле \"Отп токен\" значением {text}")
    public AuthPage fillFieldOtpToken(String text) {
        otpToken.sendKeys(text);
        return this;
    }


}
