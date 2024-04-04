package test.ui.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
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
    private static Stream<Arguments> subCategoryProvider() {
        return Stream.of(
                Arguments.of("Красота и спорт", "Тренажеры и инвентарь", CategoriesData.preparedEquipmentSubCategories),
                Arguments.of("Работа и офис", "Издательство и печать", CategoriesData.preparedPublishingAndPrinting),
                Arguments.of("Авто и Мото", "Автоэлектроника", CategoriesData.preparedAutoelectronics),
                Arguments.of("Детям и мамам", "Игры на улице и спорт", CategoriesData.preparedOutdoorGamesAndSports)
        );
    }

    private static Stream<Arguments> checkingPriceInCartTestProvider() {
        return Stream.of(
                Arguments.of("Компьютеры и сети", "SSD", "SILICON-POWER", 200.0)
        );
    }

    private static Stream<Arguments> instockTestProvider(){
        return Stream.of(
                Arguments.of("Компьютеры и сети", "Принтеры и МФУ", "PANTUM"),
                Arguments.of("Компьютеры и сети", "SSD", "KINGSTON"),
                Arguments.of("Дом и сад", "IP-камеры", "XIAOMI"),
                Arguments.of("Электроника", "Зарядные устройства", "APPLE")
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

    @DisplayName("Тест подкатегорий")
    @Description("Запрашивает реальный список подкатегории и сравнивает с подготовленным списком")
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
    @ParameterizedTest(name = "Проверка корректности отображения товаров бренда {2} на странице")
    @MethodSource("instockTestProvider")
    public void labelCatalog_settings_instockTest(String category, String menu_item, String brand) {
        MainPage mainPage = new MainPage();
        mainPage.menuItemClick(category, menu_item);
        itemsPage.filtration(brand, true);
        ElementsCollection items = itemsPage.actualOnPageItems();
        for (SelenideElement item : items) {
            System.out.println(item.text());
            item.$x("./..//span[@class='catalog-item-stock instock_yes']").shouldBe(Condition.visible);
            assertThat("Текст элемента не содержит названия бренда", item.text().toLowerCase(), containsString(brand.toLowerCase()));
        }

    }

    @DisplayName("Проверка корректности добавления товаров в корзину")
    @Description("Происходит переход на страницу с товарами, выбирается нужное число случайных товаров на странице, " +
            "происходит проверка добавленных товаров и товаров, находящихся в корзине, проверка общей суммы корзины")
    @ParameterizedTest(name = "Проверка корректности добавления товаров в корзину")
    @MethodSource("checkingPriceInCartTestProvider")
    public void checkingPriceInCartTest(String category, String menu_item, String brand, Double price) {
        MainPage mainPage = new MainPage();
        mainPage.menuItemClick(category, menu_item);
        itemsPage.filtration(brand, false);
        List<ItemModel> listOfAddedToBasketItems = basketPage.addedToBasketItems(price, 7);
        double itemsPricesSum = basketPage.sumOfItems(listOfAddedToBasketItems);
        Set<ItemModel> setOfAddedToBasketItems = new HashSet<>(listOfAddedToBasketItems);
        mainPage.basketButtonClick();
        double basketTotalPricesSum = basketPage.basketTotalPrice();
        Set<ItemModel> setOfActualInBasketItems = basketPage.listOfActualInBasketItems();
        assertThat(setOfAddedToBasketItems, equalTo(setOfActualInBasketItems));
        assertThat(Math.round(itemsPricesSum), equalTo(Math.round(basketTotalPricesSum)));
    }
}





