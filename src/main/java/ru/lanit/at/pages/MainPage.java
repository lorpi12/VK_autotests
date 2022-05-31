package ru.lanit.at.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ru.lanit.at.utils.web.annotations.Name;
import ru.lanit.at.utils.web.pagecontext.WebPage;

import static com.codeborne.selenide.Selenide.$x;

@Name(value = "Главная страница")
public class MainPage extends WebPage {

    @Name("ссылка сотрудники")
    private SelenideElement linkEmployees = $x("//tr[@class='model-employee']/th[@scope='row']/a");

    @Step("Нажать на cсылку \"сотрудники\"")
    public void clickOnLinkEmployees() {
        linkEmployees.click();
    }


}
