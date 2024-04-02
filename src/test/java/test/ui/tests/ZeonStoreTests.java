package test.ui.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import test.ui.datagenerator.CategoriesData;
import test.ui.model.ItemModel;
import test.ui.pageObjects.BasketPage;
import test.ui.pageObjects.ItemsPage;
import test.ui.pageObjects.MainPage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.sleep;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ZeonStoreTests extends TestBase {
    ItemsPage itemsPage = new ItemsPage();
    BasketPage basketPage = new BasketPage();

    private static Stream<Arguments> categoryProvider() {
        return Stream.of(
                Arguments.of("Компьютеры и сети", CategoriesData.preparedComputersAndNetworkCategories),
                Arguments.of("Электроника", CategoriesData.preparedElectronicsCategories),
                Arguments.of("Красота и спорт", CategoriesData.preparedBeautyAndSportCategories),
                Arguments.of("Бытовая техника", CategoriesData.preparedAppliancesCategories)
        );
    }

    @DisplayName("Тест разделов")
    @Description("Запрашивает реальный список категорий разделов и сравнивает с подготовленным списком")
    @ParameterizedTest(name = "Проверка раздела: {0}")
    @MethodSource("categoryProvider")
    public void sectionTest(String section, List<String> expectedCategories) {
        MainPage mainPage = new MainPage();
        mainPage.catalogButtonClick();
        mainPage.catalogCategoryButtonClick(section);
        assertThat(mainPage.actualCategories.texts(), equalTo(expectedCategories));
    }


    private static Stream<Arguments> subCategoryProvider() {
        return Stream.of(
                Arguments.of("Красота и спорт", "Тренажеры и инвентарь", CategoriesData.preparedEquipmentSubCategories),
                Arguments.of("Работа и офис", "Издательство и печать", CategoriesData.preparedPublishingAndPrinting),
                Arguments.of("Авто и Мото", "Автоэлектроника", CategoriesData.preparedAutoelectronics),
                Arguments.of("Детям и мамам", "Игры на улице и спорт", CategoriesData.preparedOutdoorGamesAndSports)
        );
    }

    @DisplayName("Тест подкатегорий")
    @Description("Запрашивает реальный список подкатегорий {1} в категории {0} и сравнивает с подготовленным списком")
    @ParameterizedTest(name = "Проверка подкатегории: {1}")
    @MethodSource("subCategoryProvider")
    public void categoryTest(String category, String subCategory, List<String> expectedSubCategories) {
        MainPage mainPage = new MainPage();
        mainPage.catalogButtonClick();
        mainPage.catalogCategoryButtonClick(category);
        assertThat(mainPage.actualSubCategories(subCategory).texts(), equalTo(expectedSubCategories));
    }

    @DisplayName("Проверка корректности отображения товара на странице")
    @Description("Происхоит переход на страницу с товарами, отображаются товары только в наличии. Проверка наличия наименования бренда в названии товара, проверка плашки 'Есть в наличии'")
    @Test
    public void labelCatalog_settings_instockTest() {
        MainPage mainPage = new MainPage();
        mainPage.catalogButtonClick();
        mainPage.catalogCategoryButtonClick("Компьютеры и сети");
        mainPage.subCategoryItemClick("Принтеры и МФУ");
        itemsPage.checkBoxBrandClick("PANTUM");
        itemsPage.inStockButtonActivateClick();
        $x("//span[@class='help']").shouldNotBe(Condition.visible); //ожидание, пока останутся только товары "в наличии"
        sleep(1000);
        ElementsCollection goods = itemsPage.actualOnPageGoods();
        for (SelenideElement element : goods) {
            System.out.println(element.text());
            element.shouldHave(Condition.text("Pantum"));
            $x("//span[@class='catalog-item-stock instock_yes']").shouldBe(Condition.visible);
        }
    }
    @DisplayName("Проверка корректности добавления товаров в корзину")
    @Description("Происходит переход на страницу с товарами, выбирается нужное число случайных товаров на странице, " +
            "происходит проверка добавленных товаров и товаров, находящихся в корзине, проверка общей суммы корзины")
    @Test
    public void checkingPriceInCartTest() {
        MainPage mainPage = new MainPage();
        mainPage.catalogButtonClick();
        mainPage.catalogCategoryButtonClick("Компьютеры и сети");
        mainPage.subCategoryItemClick("SSD");
        itemsPage.inStockButtonActivateClick();
        List<ItemModel> listOfAddedToBasketItems = basketPage.listOfAddedToBasketItems(100.0, 7);
        double itemsPricesSum = listOfAddedToBasketItems.stream().mapToDouble(ItemModel::getPrice).sum();
        Set<ItemModel> setOfAddedToBasketItems = new HashSet<>(listOfAddedToBasketItems);
        sleep(2000);
        mainPage.basketButtonClick();
        Set<ItemModel> setOfActualInBasketItems = basketPage.listOfActualInBasketItems();
        assertThat(setOfAddedToBasketItems, equalTo(setOfActualInBasketItems));
        double basketTotalPricesSum = Double.parseDouble($x("//span[@class='total-clubcard-price summa_car1']")
                .text().replace("руб", "").replace("коп.", "").replace(" ", ""));
        assertThat(Math.round(itemsPricesSum), equalTo(Math.round(basketTotalPricesSum)));
    }
}





