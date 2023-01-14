package ru.lanit.at.pages;

import com.codeborne.selenide.SelenideElement;
import ru.lanit.at.utils.web.pagecontext.WebPage;

import static com.codeborne.selenide.Selenide.$x;

public class MainPage extends WebPage {

    private SelenideElement mySyte = $x("//li[@id='l_pr']/a");

    private SelenideElement messanger = $x("//li[@id='l_msg']/a");

    private SelenideElement communities = $x("//li[@id='l_gr']/a");

    private SelenideElement photo = $x("//li[@id='l_ph']/a");


    public void clickMySyte() {
        mySyte.click();
    }

    public void clickMessanger() {
        messanger.click();
    }

    public void clickCommunities() {
        communities.click();
    }

    public void clickPhoto() {
        photo.click();
    }

}
