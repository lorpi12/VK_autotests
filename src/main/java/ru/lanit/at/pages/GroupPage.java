package ru.lanit.at.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import ru.lanit.at.utils.Sleep;
import ru.lanit.at.utils.web.pagecontext.WebPage;

import static com.codeborne.selenide.Selenide.$x;

public class GroupPage extends WebPage {

    private SelenideElement createDiscussion = $x("//a[@title='Обсуждения']");

    private SelenideElement addDiscussion = $x("//div[@data-tab='discussions']//a");

    private SelenideElement titleDiscussion = $x("//input[@id='bnt_title']");

    private SelenideElement textDiscussion = $x("//textarea[@id='bnt_text']");

    private SelenideElement buttonCreateDiscussion = $x("//button[@id='bnt_subm']");

    private SelenideElement editDiscussion = $x("//div[@class='page_block_header_extra _header_extra']/button");

    private SelenideElement pinDiscussion = $x("//div[@class='checkbox _bet_fixed']");

    private SelenideElement confirmPin = $x("//button[@class='FlatButton FlatButton--primary FlatButton--size-m']");

    private SelenideElement commentAreaInput = $x("//div[@class='reply_field submit_post_field']");

    private SelenideElement firstComment = $x("//div[@id='bt_rows']/div[2]");

    private SelenideElement deleteFirstComment = $x("//div[@id='bt_rows']/div[2]//a[@class='bp_delete_button bp_action fl_r']");

    private SelenideElement secondComment = $x("//div[@id='bt_rows']/div[3]");

    private SelenideElement editSecondComment = $x("//div[@id='bt_rows']/div[3]//a[@class='bp_edit_button bp_action fl_r']");

    private SelenideElement textareaSecondComment = $x("//div[@id='bt_rows']/div[3]//textarea");

    private SelenideElement confirmEditSecondComment = $x("//div[@id='bt_rows']/div[3]//button[@id='bpe_save']");

    private SelenideElement returnToGroup = $x("//a[@class='ui_crumb'][1]");

    private SelenideElement buttonPageActions = $x("//div[@class='ui_actions_menu_wrap _ui_menu_wrap page-group-actions page-group-actions--event']");

    private SelenideElement leaveGroup = $x("//div[@id='page_subscribers']//div[@class='ui_actions_menu _ui_menu page-group-actions__inner']/div[1]");

    private SelenideElement leaveGroupConfirm = $x("//button[@class='FlatButton FlatButton--primary FlatButton--size-m']");

    @Step("Нажать на кнопку 'создать дискуссию'")
    public void clickCreateDiscussion() {
        createDiscussion.click();
        addDiscussion.click();
    }

    @Step("Заполнить поле 'заголовок дискуссии'")
    public void setTitleDiscussion(String title) {
        titleDiscussion.sendKeys(title);
    }

    @Step("Заполнить поле 'текст дискуссии'")
    public void setTextDiscussion(String text) {
        textDiscussion.sendKeys(text);
    }

    @Step("Нажать кнопку создания дискуссии")
    public void clickButtonCreateDiscussion() {
        buttonCreateDiscussion.click();
    }

    @Step("Нажать кнопку изменить дискуссию")
    public void clickEditDiscussion() {
        editDiscussion.click();
    }

    @Step("Закрепить дискуссию")
    public void clickPinDiscussion() {
        pinDiscussion.click();
    }

    @Step("Подтвердить закрепление дискуссии")
    public void clickConfirmPin() {
        confirmPin.click();
    }

    @Step("создать коммент")
    public void createComment(String text) {
        commentAreaInput.click();
        commentAreaInput.sendKeys(text);
        commentAreaInput.sendKeys(Keys.ENTER);
    }

    @Step("Изменить второй коммент")
    public void editSecondComment(String text) {
        secondComment.hover();
        editSecondComment.hover().click();
        textareaSecondComment.clear();
        textareaSecondComment.sendKeys(text);
        confirmEditSecondComment.click();
    }

    @Step("Удалить первый коммент")
    public void deleteFirstComment() {
        firstComment.hover();
        deleteFirstComment.hover().click();
    }

    @Step("Выйти из группы")
    public void leaveInGroup() {
        returnToGroup.click();
        buttonPageActions.hover();
        leaveGroup.hover().click();
        leaveGroupConfirm.click();
    }

}
