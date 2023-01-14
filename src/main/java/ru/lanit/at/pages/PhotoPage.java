package ru.lanit.at.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.lanit.at.utils.Sleep;
import ru.lanit.at.utils.web.pagecontext.WebPage;


import java.io.File;

import static com.codeborne.selenide.Selenide.$x;

public class PhotoPage extends WebPage {

    private SelenideElement createAlbum = $x("//button[@id='photos_add_album_btn']");

    private SelenideElement titleAlbum = $x("//input[@id='new_album_title']");

    private SelenideElement privacyControlButton = $x("//div[@class='photos_privacy_controls']/div[1]//button");

    private SelenideElement privacyControlItem = $x("//button[@id='privacy_item3']");

    private SelenideElement createAlbumButton = $x("//button[@class='FlatButton FlatButton--primary FlatButton--size-m']");

    private SelenideElement addPhotoArea = $x("//div[@class='upload_btn_wrap']//input[@id='photos_upload_input']");

    private SelenideElement photoEditButton = $x("//div[@class='photos_photo_edit_row_thumb ']");

    private SelenideElement moreButton = $x("//div[@class='pv_bottom_actions']/a[4]");

    private SelenideElement makeCoverAlbum = $x("//div[@class='pv_more_acts']/div[9]");

    private SelenideElement markPeople = $x("//div[@class='pv_bottom_actions']/a[2]");

    private SelenideElement photoZone = $x("//div[@id='pv_photo']/img");

    private SelenideElement peopleMarked = $x("//div[@id='layer']/div[3]//div[@class='ui_scroll_content clear_fix']/div[@data-name='Дмитрий Пупкин']");

    private SelenideElement commentArea = $x("//div[@class='reply_field submit_post_field']");

    private SelenideElement markPeopleConfirm = $x("//button[@class='FlatButton FlatButton--primary FlatButton--size-m']");

    private SelenideElement selectPrivateAlbum = $x("//div[@id='photos_container_albums']/div[3]/a");

    private SelenideElement photoContainer = $x("//div[@id='photos_container_photos']//a");

    private SelenideElement transferPhoto = $x("//div[@class='pv_more_acts']/div[8]");

    private SelenideElement selectAlbumToTransfer = $x("//div[@class='photos_album_thumb crisp_image']");

    private SelenideElement photoEditButtonClose = $x("//div[@class='pv_close_btn']");

    private SelenideElement editAlbum = $x("//div[@class='photos_album_intro_info']/span[3]/a");

    private SelenideElement deleteAlbum = $x("//button[@id='album_delete_action']");

    private SelenideElement deleteAlbumConfirm = $x("//button[@class='FlatButton FlatButton--primary FlatButton--size-m']");

    private SelenideElement selectPublicAlbum = $x("//div[@id='photos_container_albums']/div[2]/a");


    public void clickCreateAlbum() {
        createAlbum.click();
    }

    public void setTitleAlbum(String name) {
        titleAlbum.sendKeys(name);
    }

    public void setPrivacyControl() {
        privacyControlButton.click();
        privacyControlItem.click();
    }

    public void clickCreateAlbumButton() {
        createAlbumButton.click();
    }

    public void addPhoto() {
        addPhotoArea.uploadFile(new File("src/test/resources/photoFile/kotik.jpg"));
    }

    public void makeCoverAlbum() {
        photoEditButton.click();
        moreButton.hover();
        makeCoverAlbum.click();
    }

    public void markPeople() {
        markPeople.click();
        Sleep.pauseSec(1);
        photoZone.click();
        peopleMarked.click();
        markPeopleConfirm.click();
    }

    public void addComment(String comment) {
        commentArea.sendKeys(comment, Keys.ENTER);
    }

    public void clickSelectPrivateAlbum() {
        selectPrivateAlbum.click();
    }

    public void transferToPublicAlbum() {
        photoContainer.click();
        moreButton.hover();
        transferPhoto.click();
        selectAlbumToTransfer.click();
        photoEditButtonClose.click();
    }

    public void deleteAlbum() {
        editAlbum.click();
        deleteAlbum.click();
        deleteAlbumConfirm.click();
    }

    public void deletePublicAlbum(){
        selectPublicAlbum.click();
        deleteAlbum();
    }

}
