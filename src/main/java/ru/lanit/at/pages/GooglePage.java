package ru.lanit.at.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ru.lanit.at.utils.web.annotations.Name;
import ru.lanit.at.utils.web.pagecontext.WebPage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

@Name(value = "Google")
public class GooglePage extends WebPage {

    @Name("поле поиска")
    private SelenideElement searchField = $x("//input[@name='q']");

    @Name("кнопка поиска")
    private SelenideElement searchButton = $x("//*[@name='btnK']");

    @Step("Заполнить поле поиска значением {text}")
    public GooglePage fillSearchField(String text) {
        searchField.sendKeys(text);
        return this;
    }

    @Step("Нажать на кнопку поиска")
    public GooglePage clickOnSearchButton() {
        searchButton.click();
        return this;
    }

    @Step("На странице отображается кнопка поиска")
    public GooglePage searchButtonShouldBeVisible() {
        searchButton.shouldBe(Condition.visible);
        return this;
    }
}
