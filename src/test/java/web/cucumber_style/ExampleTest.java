package web.cucumber_style;

import io.qameta.allure.Step;
import org.testng.annotations.Test;
import ru.lanit.at.steps.web.DebugWebSteps;
import ru.lanit.at.steps.web.WebActionWebSteps;
import ru.lanit.at.steps.web.WebCheckWebSteps;
import ru.lanit.at.steps.web.WindowWebSteps;
import ru.lanit.at.utils.web.pagecontext.PageManager;
import web.MainTest;

public class ExampleTest {
    /* Для осуществления поиска элементов в контексте конкртеной страницы */
    private final PageManager pageManager = new PageManager();

    /* Классы шагов */
    private final DebugWebSteps debugWebSteps = new DebugWebSteps(pageManager);
    private final WindowWebSteps windowWebSteps = new WindowWebSteps(pageManager);
    private final WebActionWebSteps webActionWebSteps = new WebActionWebSteps(pageManager);
    private final WebCheckWebSteps webCheckWebSteps = new WebCheckWebSteps(pageManager);

    @Test
    public void exampleTest() {
        step1();
        step2();
    }

    private void step1() {
        debugWebSteps.stepNumber("1");
        windowWebSteps.open("http://178.154.246.238:58082/");
        windowWebSteps.setPage("Google");
        webActionWebSteps.fillTheField("поле поиска", "Погода в Москве");
        webCheckWebSteps.textVisibleOnPage("Погода в Ижевске");
    }

    private void step2() {
        debugWebSteps.stepNumber("2");
        webCheckWebSteps.elementAppearOnThePage("кнопка поиска");
        webActionWebSteps.clickOnElement("кнопка поиска");
        windowWebSteps.setPage("Google страница результатов");
        webCheckWebSteps.elementAppearOnThePage("виджет погоды");
    }
}
