package ru.lanit.at.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ru.lanit.at.utils.web.annotations.Name;
import ru.lanit.at.utils.web.pagecontext.WebPage;

import static com.codeborne.selenide.Selenide.$x;

@Name(value = "Сотрудники")
public class EmployeePage extends WebPage {

    @Name("ДОБАВИТЬ НОВЫЙ СОТРУДНИК+")
    private SelenideElement buttonAddNewEmployee = $x("//a[@class='addlink']");

    @Name("линк успешное добавление сотрудника")
    private SelenideElement linkSuccessAddEmployee = $x("//li[@class='success']/a");


    @Step("Нажать на кнопку \"ДОБАВИТЬ НОВЫЙ СОТРУДНИК+\"")
    public void clickOnButtonAddNewEmployee() {
        buttonAddNewEmployee.click();
    }

    @Step("Получить значение ссылки на успешное добавление сотрудника")
    public String getValueByLink() {
        return linkSuccessAddEmployee.getText();
    }


}
