package ru.lanit.at.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import ru.lanit.at.utils.Sleep;
import ru.lanit.at.utils.web.pagecontext.WebPage;

import static com.codeborne.selenide.Selenide.$x;

public class CommunityPage extends WebPage {

    private SelenideElement newGroup = $x("//a[@class='FlatButton FlatButton--primary FlatButton--size-s']");

    private SelenideElement groupTitleInterests = $x("//div[@class='groups_create_tile groups_create_tile_interests']");

    private SelenideElement groupTitle = $x("//input[@id='groups_create_box_title']");

    private SelenideElement selectTheme = $x("//div[@id='groups_create_box_category_wrap_1']//input[@type='text']");

    private SelenideElement buttonCreateGroup = $x("//button[@class='FlatButton FlatButton--primary FlatButton--size-m']");

    private SelenideElement cancelTutorial = $x("//div[@class='groups_welcome_box_close']");

    @Step("Создание новой группы")
    public void clickNewGroup() {
        newGroup.click();
    }

    @Step("Нажать по полю 'интересы'")
    public void clickGroupTitleInterests() {
        groupTitleInterests.click();
    }

    @Step("Заполнить поле 'название'")
    public void setGroupTitle(String name) {
        groupTitle.sendKeys(name);
    }

    @Step("Заполнить поле 'тема'")
    public void setGroupTheme() {
        selectTheme.click();
        selectTheme.sendKeys(Keys.ENTER);
    }

    @Step("Нажать кнопку создания группы")
    public void clickButtonCreateGroup() {
        buttonCreateGroup.click();
    }

    public void clickCancelTutorial() {
        cancelTutorial.click();
    }


}
