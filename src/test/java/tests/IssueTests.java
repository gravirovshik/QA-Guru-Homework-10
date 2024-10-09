package tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.*;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.attachment;
import static io.qameta.allure.Allure.step;
import static org.openqa.selenium.By.linkText;

@Feature("Проверка вкладки Issues")
@DisplayName("Проверка вкладки Issues")
public class IssueTests extends BaseTests {

    private static final String dataSearch = "gravirovshik/QA-Guru-Homework-10";
    private static final String issueName = "Issues";

    @Test
    @Story("Проверка на 'чистом Selenide'")
    @Owner("Belov Roman")
    @Severity(SeverityLevel.CRITICAL)
    @Link(value = "Test page", url = "https://github.com/qa-guru/qa_guru_14_10/issues")
    @DisplayName("Проверка на 'чистом Selenide', что в Issues содержится Issue с именем '" + issueName + "'.")
    void onlySelenideTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        open("/");
        $(".search-input").click();
        $("#query-builder-test").setValue(dataSearch).pressEnter();
        $(linkText("gravirovshik/QA-Guru-Homework-10")).click();
        $("a#issues-tab").click();
        $("#issues-tab").shouldHave(text("Issues"));
    }

    @Test
    @Story("Проверка с использованием lambda-выражений")
    @Owner("Belov Roman")
    @Severity(SeverityLevel.CRITICAL)
    @Link(value = "Test page", url = "https://github.com/gravirovshik/QA-Guru-Homework-10/issues")
    @DisplayName("Проверка с использованием lambda-выражений, что в Issues содержится Issue с именем '" + issueName + "'.")
    void lambdaStepTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        step("Открываем главную страницу Github.", () -> {
            open("/");
        });
        step("В поле поиска пишем '" + dataSearch + "' и нажимаем [enter].", () -> {
            $(".search-input").click();
            $("#query-builder-test").setValue(dataSearch).pressEnter();
        });
        step("Ищем в списке нужную ссылку и кликаем на неё.", () -> {
            $(linkText("gravirovshik/QA-Guru-Homework-10")).click();
        });
        step("Переходим на вкладку 'Issues'.", () -> {
            $("a#issues-tab").click();
        });
        step("Проверяем, что есть Issue с именем '" + issueName + "'.", () -> {
            $("#issues-tab").shouldHave(text("Issues"));
            attachment("Source", webdriver().driver().source());
        });
    }

    @Test
    @Story("Проверка с использованием аннотации @Step")
    @Owner("Belov Roman")
    @Severity(SeverityLevel.BLOCKER)
    @Link(value = "Test page", url = "https://github.com/gravirovshik/QA-Guru-Homework-10/issues")
    @DisplayName("Проверка с использованием аннотации @Step, что в Issues содержится Issue с именем '" + issueName + "'.")
    void stepAnnotationTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        WebSteps steps = new WebSteps();

        steps
                .openMainPage()
                .searchRepositories(dataSearch)
                .selectRepository()
                .selectIssuesTab()
                .checkIssueName(issueName)
                .takeScreenshot();
    }
}