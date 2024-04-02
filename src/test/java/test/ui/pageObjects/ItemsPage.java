package test.ui.pageObjects;

import com.codeborne.selenide.ElementsCollection;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Selenide.*;

public class ItemsPage {
    WebElement checkBox = $x("//span[@class='ui-checkbox catalog-settings-instock']/input");
    @Step("Получение списка товаров на странице")
    public ElementsCollection actualOnPageItems() {
        return $$x("//div[@class='catalog-item-title']");
    }
    public void checkBoxBrandClick(String brand) {
        $x(String.format("//li[.//span[contains(text(), '%s')]]", brand)).click();
    }
    public void inStockButtonActivateClick() {
        if (!checkBox.isSelected()) {
            $x("//input[@id='catalog_settings_instock']").click();
        }
    }
    @Step("Фильтрация элементов на странице товаров")
    public void filtration(String brand, boolean useBrandFiltration){
        inStockButtonActivateClick();
        if (useBrandFiltration) {
            checkBoxBrandClick(brand);
            sleep(2000);
        }
        $x("//span[@class='help']").should(disappear); //ожидание, пока останутся только товары "в наличии"


    }


}
