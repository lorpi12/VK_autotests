package web.testng_style;

import io.qameta.allure.Step;
import org.testng.annotations.Test;
import ru.lanit.at.actions.WebChecks;
import ru.lanit.at.pages.GooglePage;
import ru.lanit.at.pages.GoogleResultPage;
import ru.lanit.at.steps.web.WindowWebSteps;

public class ExampleTest {
    /*
    Обычно в TDD контекст страниц не используется, поэтому pageManager = null
    WindowWebSteps используется вне контекста для общих шагов
    */
    private final WindowWebSteps windowWebSteps = new WindowWebSteps(null);

    /* Классы страниц */
    private final GooglePage googlePage = new GooglePage();
    private final GoogleResultPage googleResultPage = new GoogleResultPage();

    @Test
    public void exampleTest() {
        step1();
        step2();
    }

    @Step("Шаг №1")
    private void step1() {
        windowWebSteps.open("https://www.google.ru/");
        googlePage.fillSearchField("Погода в Москве");
        WebChecks.textAbsentOnPage("Погода в Ижевске", null);
    }

    @Step("Шаг №2")
    private void step2() {
        googlePage
                .searchButtonShouldBeVisible()
                .clickOnSearchButton();
        googleResultPage.weatherWidgetShouldBeVisible();
    }
}
