/**
 * Главная страница сайта www.777555.by
 */
package test.ui.pageObjects;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class MainPage {

    public MainPage(String url) {
        open(url);
    }

    private final SelenideElement catalogButton = $x("//div[@class='catalog hidden-xs hidden-sm']");
    private final SelenideElement beautyAndSportButton = $x("//li[@data-name='Красота и спорт']/button");
    private final SelenideElement electronicsButton = $x("//li[@data-name='Электроника']/button");

    public void catalogButtonClick() {
        catalogButton.click();
    }

    public void beautyAndSportButtonClick() {
        beautyAndSportButton.click();
    }

    public void electronicsButtonClick() {
        electronicsButton.click();
    }
}

