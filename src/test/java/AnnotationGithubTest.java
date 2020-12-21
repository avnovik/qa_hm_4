import com.codeborne.selenide.Condition;
import com.codeborne.selenide.conditions.Visible;
import config.ConfigHelper;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class AnnotationGithubTest {
    private final static String Login = ConfigHelper.getLogin();
    private final static String Password = ConfigHelper.getPassword();
    private final static String Link = "https://github.com";
    private final static String Repository = "qa_hm_3";
    private final static String Issues_Title = "Homework";
    private final static String Issues_Comment = "Write a test for creating an Issue in the repository through the Web interface.";

    @Test
    @DisplayName("Test with annotation")
    @Owner("avnovikov")
    void stepGithubTest() {
        final BaseSteps steps = new BaseSteps();
        steps.successfulLogin();
        steps.findRepository(Repository);
        steps.creatingIssue(Issues_Title, Issues_Comment);
        steps.deleteIssue(Issues_Title);
    }

    public static class BaseSteps {

        @Step("Successful login on the main page")
        public void successfulLogin() {
            open(Link);
            $("[href='/login']").click();
            $("#login_field").val(Login);
            $("#password").val(Password);
            $("[value=\"Sign in\"]").click();
            $("body").shouldHave(text(Login));
        }

        @Step("Find repository")
        public  void  findRepository(String repo) {
            $("[aria-label=\"View profile and more\"]").click();
            $(byText("Your repositories")).click();
            $(byText(Repository)).click();
        }

        @Step("Creating an Issue")
        public void creatingIssue(String text_title, String text_comment) {
            $("[data-content=\"Issues\"]").click();
            sleep(250);  // я хз но без этой паузы он не видит кнопку "New issue", был бы рад обьяснению почему.
            $(".repository-content .btn-primary").shouldBe(visible).click();
            $("#issue_title").val(Issues_Title);
            $("#issue_body").val(Issues_Comment);
            $(byText("Assignees")).click();
            $(".select-menu-list").find(byText(Login)).click();
            $(".select-menu-item").sendKeys(Keys.ESCAPE);
            $(byText("Labels")).click();
            $(byText("help wanted")).click();
            $(byText("question")).click();
            $("[role=\"menuitemcheckbox\"]").sendKeys(Keys.ESCAPE);
            $$("button[type=\"submit\"]").find(text("Submit new issue")).click();
            $(".gh-header-show").shouldHave(text(Issues_Title));
        }

        @Step("Delete an Issue and exit")
        public void deleteIssue(String text_title) {
            $(byText("Delete issue")).click();
            $(byText("Delete this issue")).click();
            $(".repository-content").shouldNotHave(text(Issues_Title));
            $("[aria-label=\"View profile and more\"]").click();
            $(".logout-form").click();
            $("main").shouldHave(text("Where the world"));
        }
    }
}

