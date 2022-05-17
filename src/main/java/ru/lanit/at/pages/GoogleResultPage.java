package ru.lanit.at.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.lanit.at.utils.web.annotations.Name;
import ru.lanit.at.utils.web.pagecontext.WebPage;

import static com.codeborne.selenide.Selenide.$;

@Name(value = "Google страница результатов")
public class GoogleResultPage extends WebPage {

    @Name("виджет погоды")
    private SelenideElement searchField = $(By.id("wob_wc"));

    @Step("На странице виден виджет погоды")
    public void weatherWidgetShouldBeVisible() {
        searchField.shouldBe(Condition.visible);
    }
}