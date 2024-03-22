package test.ui.tests;

import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import test.ui.datagenerator.CategoriesData;
import test.ui.pageObjects.MainPage;

import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CatalogSectionsTests extends TestBase {

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
                Arguments.of("Красота и спорт", "Тренажеры и инвентарь", CategoriesData.preparedEquipmentSubCategories)
        );
    }

    @DisplayName("Тест подкатегорий")
    @Description("Запрашивает реальный список подкатегорий {1} в категории {0} и сравнивает с подготовленным списком")
    @ParameterizedTest(name = "Проверка подкатегории: {1}")
    @MethodSource("subCategoryProvider")
    public void categoryTest(String category, String subCategory, List<String> expectedSubCategories){
        MainPage mainPage = new MainPage();
        mainPage.catalogButtonClick();
        mainPage.catalogCategoryButtonClick(category);
        assertThat(mainPage.actualSubCategories(subCategory).texts(), equalTo(expectedSubCategories));
    }
}

