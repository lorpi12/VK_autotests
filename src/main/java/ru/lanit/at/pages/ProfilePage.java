package ru.lanit.at.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
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

    public void sendSmallInfo(String info) {
        smallInfo.sendKeys(info);
    }

    public String getSmallInfo() {
        return smallInfo.getText();
    }

    public String getFamilyStatus() {
        return familyStatusInfo.getValue();
    }

    public String getCity() {
        return city.getValue();
    }

    public String editFamilyStatus(int value) {
        if (familyStatusInfo.getValue().equals("0")) {
            selectFamilyStatus(value);
            return "0";
        }
        return familyStatusInfo.getValue();
    }

    public void selectFamilyStatus(int value) {
        familyStatus.click();
        familyValue.$x(String.format(".//li[%d]", value)).click();
    }

    public void clearCity() {
        city.clear();
    }

    public void clearSmallInfo() {
        smallInfo.clear();
    }

    public void sendCity(String data) {
        city.sendKeys(data);
    }

    public void clickSaveButton() {
        saveButton.click();
    }

    public void uploadPhoto() {
        photo.hover();
        photoUpload.shouldBe(Condition.exist).click();
        photoSelectFile.uploadFile(new File("src/test/resources/photoFile/kotik.jpg"));
        photoSaveAndContinue.click();
        photoSaveAndContinue.click();
        photoSaveAndContinue.click();
    }

    public void verifyPhotoUpload() {
        photoImg.shouldNot(Condition.attribute("src", "https://vk.com/images/camera_100.png"));
    }

    public void deletePhoto() {
        photo.hover();
        photoDelete.click();
        photoDeleteConfirm.click();
    }


}
