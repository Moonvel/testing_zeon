/**
 * Главная страница сайта www.777555.by
 */
package test.ui.pageObjects;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import test.ui.properties.PropsHelper;

import static com.codeborne.selenide.Selenide.*;

public class MainPage {

    PropsHelper propsHelper = PropsHelper.getInstance();

    public MainPage() {
        open(propsHelper.getProperty("BASE_URL"));
    }

    private final SelenideElement catalogButton = $x("//div[@class='catalog hidden-xs hidden-sm']");
    private final SelenideElement basketButton = $x("//a[@href='/cart.php?id=']");
    public ElementsCollection actualCategories = $$x("//div[@class='category-item mobile-modal opened']//div[contains(@class, 'subcategory-name')]");

    @Step("Нажатие кнопки каталог")
    public void catalogButtonClick() {
        catalogButton.click();
    }

    @Step("Переход в категорию {0}")
    public void catalogCategoryButtonClick(String catalogCategoryButtonName) {
        $x(String.format("//li[@data-name='%s']/button", catalogCategoryButtonName)).click();
    }


    public ElementsCollection actualSubCategories(String subCategory) {
        return $$x(String.format("//div[@class='subcategory-item mobile-modal' and @data-name='%s']//li[@class='menu-item']", subCategory));
    }
    @Step("Переход в корзину")
    public void basketButtonClick() {
        basketButton.click();
    }

    @Step("Переход в товары {0}")
    public void menuItemClick(String category, String menu) {
        catalogButtonClick();
        catalogCategoryButtonClick(category);
        $x(String.format("//a[contains(text(), '%s')]", menu)).click(); //клик на название товаров
    }
}
