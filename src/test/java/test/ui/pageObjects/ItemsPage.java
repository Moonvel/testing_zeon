package test.ui.pageObjects;

import com.codeborne.selenide.ElementsCollection;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class ItemsPage {
    WebElement checkBox = $x("//span[@class='ui-checkbox catalog-settings-instock']/input");
    @Step("Получение списка товаров на странице")
    public ElementsCollection actualOnPageItems() {
        return $$x("//div[@class='catalog-item-title']");
    }

    @Step("Нажатие чекбокса фильтрации бренда")
    public void checkBoxBrandClick(String brand) {
        $x(String.format("//li[.//span[contains(text(), '%s')]]", brand)).click();
        $x("//ul[@style]").shouldBe(exist);
        $x("//ul[@class='list_catalog']").shouldHave(cssValue("opacity", "1"), Duration.ofMillis(5000L));
        //ожидание пока страница станет непрозрачной после нажатия чекбокса бренда
    }

    @Step("Нажадите слайдора в наличии")
    public void inStockButtonActivateClick() {
        if (!checkBox.isSelected()) {
            $x("//input[@id='catalog_settings_instock']").click();
        }
        $x("//span[@class='help']").should(disappear);
        //ожидание, пока останутся только товары "в наличии"
    }
    @Step("Фильтрация элементов на странице товаров")
    public void filtration(String brand, boolean useBrandFiltration){

        if (useBrandFiltration) {
            checkBoxBrandClick(brand);
        }
        inStockButtonActivateClick();
    }


}
