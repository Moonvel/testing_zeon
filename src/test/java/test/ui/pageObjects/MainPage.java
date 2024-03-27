/**
 * Главная страница сайта www.777555.by
 */
package test.ui.pageObjects;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import test.ui.properties.PropsHelper;

import static com.codeborne.selenide.Selenide.*;

public class MainPage {

    PropsHelper propsHelper = PropsHelper.getInstance();

    public MainPage() {
        open(propsHelper.getProperty("BASE_URL"));
    }

    private final SelenideElement catalogButton = $x("//div[@class='catalog hidden-xs hidden-sm']");
    public ElementsCollection actualCategories = $$x("//div[@class='category-item mobile-modal opened']//div[contains(@class, 'subcategory-name')]");


    public void catalogButtonClick() {
        catalogButton.click();
    }
    public void catalogCategoryButtonClick(String catalogCategoryButtonName) {
        $x(String.format("//li[@data-name='%s']/button", catalogCategoryButtonName)).click();
    }
    public void subCategoryItemClick(String item){
        $x(String.format("//a[contains(text(), '%s')]", item)).click();
    }
    public void checkBoxBrandClick(String brand){
        $x(String.format("//li[.//span[contains(text(), '%s')]]", brand)).click();
    }
    public void inStockButtonClick() {
        Selenide.$("#catalog_settings_instock").click();
    }

    public ElementsCollection actualSubCategories(String subCategory){
        return $$x(String.format("//div[@class='subcategory-item mobile-modal' and @data-name='%s']//li[@class='menu-item']",subCategory));
    }
    public ElementsCollection actualOnPageGoods() {
        return $$x("//div[@class='catalog-item-title']/a");
    }





}

