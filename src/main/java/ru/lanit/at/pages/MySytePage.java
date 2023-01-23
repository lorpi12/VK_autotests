package ru.lanit.at.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ru.lanit.at.utils.web.pagecontext.WebPage;

import static com.codeborne.selenide.Selenide.$x;

public class MySytePage extends WebPage {

    private SelenideElement editProfile = $x("//div[@class='ProfileHeaderButton']/a");

    @Step("Перейти в раздел изменения профиля")
    public void clickEditProfile() {
        editProfile.click();
    }
}
