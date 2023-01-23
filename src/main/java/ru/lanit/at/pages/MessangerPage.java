package ru.lanit.at.pages;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import ru.lanit.at.utils.Sleep;
import ru.lanit.at.utils.web.pagecontext.WebPage;

import static com.codeborne.selenide.Selenide.$x;

public class MessangerPage extends WebPage {

    private SelenideElement newChat = $x("//button[@aria-label='Новый чат']");

    private SelenideElement nameChat = $x("//input[@id='im_dialogs_creation_name']");

    private SelenideElement createChatButton = $x("//button[@class='FlatButton FlatButton--primary FlatButton--size-m _im_confirm_creation']");

    private SelenideElement chatSettingsButton = $x("//a[@class='_im_header_link']");

    private SelenideElement nameChatSettings = $x("//h3[@class='ChatSettingsInfo__title']/div");

    private SelenideElement nameChatSettingsArea = $x("//h3[@class='ChatSettingsInfo__title']/div/textarea");

    private SelenideElement addNewMember = $x("//div[@class='ChatSettingsMembersWidget__list']/ul/li[1]/div[1]");

    private SelenideElement selectMember = $x("//div[@data-id='194237192']");

    private SelenideElement confirmMember = $x("//div[@class='SubmitArea SubmitArea--right ChatSettingsMembers__submitArea']/button");

    private SelenideElement exitChatSettings = $x("//button[@class='PopupHeader__closeBtn']");

    private SelenideElement messageArea = $x("//div[@class='im_editable im-chat-input--text _im_text']");

    private SelenideElement editMessage = $x("//span[@aria-label='Редактировать']");

    private SelenideElement editMessageBody = $x("//div[@class='im-mess-stack _im_mess_stack '][1]//div[@class='im-mess--text wall_module _im_log_body']");

    private SelenideElement pinMessage = $x("//button[@aria-label='Закрепить сообщение']");

    private SelenideElement chatMenu = $x("//div[@class='im-page--header-more im-page--header-menu-button _im_dialog_action_wrapper']");

    private SelenideElement leaveChat = $x("//a[@data-action='leave']");

    private SelenideElement deleteMessageCheckBox = $x("//div[@class='box_controls_text']/div");

    private SelenideElement leaveChatConfirm = $x("//button[@class='FlatButton FlatButton--primary FlatButton--size-m']");

    @Step("Создать новый чат")
    public void clickNewChat() {
        newChat.click();
    }

    @Step("Задать название чата")
    public void setNameChat(String name) {
        nameChat.sendKeys(name);
    }

    @Step("Нажать на кнопку создания чата")
    public void clickCreateChatButton() {
        createChatButton.click();
    }

    @Step("Нажать на кнопку перехода в настройки")
    public void clickChatSettingsButton() {
        chatSettingsButton.click();
    }

    @Step("Задать название чата")
    public void setNameChatSettings(String name) {
        nameChatSettings.click();
        nameChatSettingsArea.sendKeys(name, Keys.ENTER);
    }

    @Step("Добавить нового участника чата")
    public void clickAddNewMember() {
        addNewMember.click();
        selectMember.click();
        confirmMember.click();
    }

    @Step("Выйти из настроек чата")
    public void exitChatSettings() {
        exitChatSettings.click();
    }

    @Step("Заполнить поле для сообщений")
    public void setMessageArea(String message) {
        messageArea.click();
        messageArea.clear();
        messageArea.sendKeys(message);
        messageArea.sendKeys(Keys.ENTER);
    }

    @Step("Изменить сообщение")
    public void editMessage(String message) {
        editMessageBody.hover();
        editMessage.hover().click();
        setMessageArea(message);
    }

    @Step("Закрепить сообщение")
    public void pinMessage() {
        Sleep.pauseSec(1);
        editMessageBody.click();
        pinMessage.click();
    }

    @Step("Выйти из чата")
    public void exitFromChat() {
        chatMenu.hover();
        leaveChat.hover().click();
        deleteMessageCheckBox.click();
        leaveChatConfirm.click();
    }


}
