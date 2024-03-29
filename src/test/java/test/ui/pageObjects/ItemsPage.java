package test.ui.pageObjects;

import com.codeborne.selenide.ElementsCollection;
import org.openqa.selenium.WebElement;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class ItemsPage {
    public void checkBoxBrandClick(String brand) {
        $x(String.format("//li[.//span[contains(text(), '%s')]]", brand)).click();
    }

    public void inStockButtonActivateClick() {
        WebElement checkBox = $x("//span[@class='ui-checkbox catalog-settings-instock']/input");
        if (!checkBox.isSelected()) {
            $x("//input[@id='catalog_settings_instock']").click();
        }
    }

    public ElementsCollection actualOnPageGoods() {
        return $$x("//div[@class='catalog-item-title']/a");
    }
}
