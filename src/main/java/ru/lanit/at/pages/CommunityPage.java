package ru.lanit.at.pages;

import com.codeborne.selenide.SelenideElement;
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

    public void clickNewGroup() {
        newGroup.click();
    }

    public void clickGroupTitleInterests() {
        groupTitleInterests.click();
    }

    public void setGroupTitle(String name) {
        groupTitle.sendKeys(name);
    }

    public void setGroupTheme() {
        selectTheme.click();
        selectTheme.sendKeys(Keys.ENTER);
    }

    public void clickButtonCreateGroup() {
        buttonCreateGroup.click();
    }

    public void clickCancelTutorial() {
        cancelTutorial.click();
    }


}
