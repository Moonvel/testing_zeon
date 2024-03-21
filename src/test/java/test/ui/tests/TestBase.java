package test.ui.tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;

import static com.codeborne.selenide.Selenide.$x;


public class TestBase {

    @BeforeAll
    static void init() {
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
    }

    protected void login(String user, String password) {
        //$("div.panel-auth.hidden-xs.hidden-sm > button").click();
        $x("//div[@class='panel-auth hidden-xs hidden-sm']/button").click();
        if ($x("//a[@href='#dialog_login']").exists()) {
            $x("//a[@href='#dialog_login']").click();
            $x("//input[@name='login']").setValue(user);
            $x("//input[@name='pwd']").setValue(password);
            // $x("//div[@class='field-btn centered']/button[@class='btn block']").click();
            $x("//button[@class='btn block' and text()='Войти в личный кабинет']").click();
        }
    }
}
