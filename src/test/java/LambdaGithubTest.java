import com.codeborne.selenide.Condition;
import com.codeborne.selenide.conditions.Visible;
import config.ConfigHelper;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class LambdaGithubTest {
    private final static String Login = ConfigHelper.getLogin();
    private final static String Password = ConfigHelper.getPassword();

    private final static String Link = "https://github.com/";
    private final static String Repository = "qa_hm_3";
    private final static String Issues_Title = "Homework";
    private final static String Issues_Comment = "Write a test for creating an Issue in the repository through the Web interface.";


    @Test
    void stepGithubTest() {
        Allure.story("Test with Lambda");
        Allure.parameter("Repository", Link + Repository);

        step("Successful login on the main page", () -> {
            open(Link);
            $("[href='/login']").click();
            $("#login_field").val(Login);
            $("#password").val(Password);
            $("[value=\"Sign in\"]").click();
            $("body").shouldHave(text(Login));
        });

        step("Find repository: " + Repository, (step) -> {
            step.parameter("name", Repository);

            $("[aria-label=\"View profile and more\"]").click();
            $(byText("Your repositories")).click();
            $(byText(Repository)).click();
        });

        step("Creating an Issue", (step) -> {
            step.parameter("Title", Issues_Title);
            step.parameter("Comment", Issues_Comment);

            $("[data-content=\"Issues\"]").click();
            sleep(500);  // я хз но без этой паузы он не видит кнопку "New issue", был бы рад обьяснению почему.
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
        });

        step("Delete an Issue: " + Issues_Title, (step) -> {
            step.parameter("name", Issues_Title);

            $(byText("Delete issue")).click();
            $(byText("Delete this issue")).click();
            $(".repository-content").shouldNotHave(text(Issues_Title));
            $("[aria-label=\"View profile and more\"]").click();
            $(".logout-form").click();
            $("main").shouldHave(text("Where the world"));
        });

    }
}
