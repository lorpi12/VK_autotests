package ru.lanit.at.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ru.lanit.at.utils.web.pagecontext.WebPage;

import static com.codeborne.selenide.Selenide.$x;

public class MainPage extends WebPage {

    private SelenideElement mySyte = $x("//li[@id='l_pr']/a");

    private SelenideElement messanger = $x("//li[@id='l_msg']/a");

    private SelenideElement communities = $x("//li[@id='l_gr']/a");

    private SelenideElement photo = $x("//li[@id='l_ph']/a");

    @Step("Перейти в раздел 'моя страница'")
    public void clickMySyte() {
        mySyte.click();
    }

    @Step("Перейти в раздел 'мессенджер'")
    public void clickMessanger() {
        messanger.click();
    }

    @Step("Перейти в раздел 'сообщества'")
    public void clickCommunities() {
        communities.click();
    }

    @Step("Перейти в раздел 'фото'")
    public void clickPhoto() {
        photo.click();
    }

}
