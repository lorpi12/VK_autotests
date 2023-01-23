package ru.lanit.at.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ru.lanit.at.utils.Sleep;
import ru.lanit.at.utils.web.pagecontext.WebPage;

import java.io.File;

import static com.codeborne.selenide.Selenide.$x;

public class ProfilePage extends WebPage {

    private SelenideElement smallInfo = $x("//textarea[@id='pedit_general_short_information']");

    private SelenideElement familyStatusInfo = $x("//div[@id='container2']//input[2]");

    private SelenideElement familyStatus = $x("//div[@id='container2']");

    private SelenideElement familyValue = $x("//ul[@id='list_options_container_2']");

    private SelenideElement saveButton = $x("//div[@class='pedit_controls clear_fix']/button");

    private SelenideElement city = $x("//input[@id='pedit_home_town']");

    private SelenideElement photo = $x("//div[@class='vkuiAvatar__children']/div");

    private SelenideElement photoUpload = $x("//div[@class='vkui__portal-root']//div[@role='button']");

    private SelenideElement photoSelectFile = $x("//div[@class='OwnerAvatarEditor__form']/input");

    private SelenideElement photoSaveAndContinue = $x("//button[@class='Button Button--primary Button--size-m']");

    private SelenideElement photoImg = $x("//div[@class='ProfileEditAvatar__wrapper']//img");

    private SelenideElement photoDelete = $x("//div[@class='vkui__portal-root']//div[@class='DropdownActionSheetContent']/div[4]");

    private SelenideElement photoDeleteConfirm = $x("//div[@class='box_controls_buttons fl_r']/button[@class='FlatButton FlatButton--primary FlatButton--size-m']");

    @Step("Заполнить краткую информацию")
    public void sendSmallInfo(String info) {
        smallInfo.sendKeys(info);
    }

    @Step("Получить текст из краткой информации")
    public String getSmallInfo() {
        return smallInfo.getText();
    }

    @Step("Получить значение семейного статуса")
    public String getFamilyStatus() {
        return familyStatusInfo.getValue();
    }

    @Step("Получить значение города")
    public String getCity() {
        return city.getValue();
    }

    @Step("Изменить семейный статус")
    public String editFamilyStatus(int value) {
        if (familyStatusInfo.getValue().equals("0")) {
            selectFamilyStatus(value);
            return "0";
        }
        return familyStatusInfo.getValue();
    }

    @Step("Выбрать семейный статус")
    public void selectFamilyStatus(int value) {
        familyStatus.click();
        familyValue.$x(String.format(".//li[%d]", value)).click();
    }

    @Step("Очистить поле город")
    public void clearCity() {
        city.clear();
    }

    @Step("Очистить поле краткая информация")
    public void clearSmallInfo() {
        smallInfo.clear();
    }

    @Step("Заполнить поле город")
    public void sendCity(String data) {
        city.sendKeys(data);
    }

    @Step("Нажать на кнопку сохранить")
    public void clickSaveButton() {
        saveButton.click();
    }

    @Step("Загрузить фото")
    public void uploadPhoto(String photoPath) {
        photo.hover();
        photoUpload.shouldBe(Condition.exist).click();
        photoSelectFile.uploadFile(new File(photoPath));
        photoSaveAndContinue.click();
        photoSaveAndContinue.click();
        photoSaveAndContinue.click();
    }

    @Step("Подтвердить загрузку фото")
    public void verifyPhotoUpload(String attribute, String expected) {
        photoImg.shouldNot(Condition.attribute(attribute, expected));
    }

    @Step("Удалить фото")
    public void deletePhoto() {
        photo.hover();
        photoDelete.click();
        photoDeleteConfirm.click();
    }


}
