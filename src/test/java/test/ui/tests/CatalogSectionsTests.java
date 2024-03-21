package test.ui.tests;

import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test.ui.DataGenerator.CategoriesData;
import test.ui.pageObjects.MainPage;
import test.ui.properties.PropsHelper;

import java.util.List;

import static com.codeborne.selenide.Selenide.$$x;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CatalogSectionsTests extends TestBase {

    private static final String BASE_URL = "https://www.777555.by/";
    PropsHelper propsHelper = PropsHelper.getInstance();


    @DisplayName("Тест раздела 'Электроника'")
    @Description("Запрашивает реальный список категорий раздела 'Электроника' и сравнивает с подготовленным списком")
    @Test
    public void testCatalogElectronics() {
        MainPage mainPage = new MainPage(BASE_URL);
        login(propsHelper.getProperty("UserLogin"), propsHelper.getProperty("UserPassword"));
        mainPage.catalogButtonClick();
        mainPage.electronicsButtonClick();
        List<String> actualElectronicsCategories =
                $$x("//div[@class='category-item mobile-modal opened']//div[@class='subcategory-name hidden-xs hidden-sm']").texts();
        assertThat(actualElectronicsCategories, equalTo(CategoriesData.preparedElectronicsCategories));
    }

    @DisplayName("Тест раздела 'Красота и Спорт'")
    @Description("Запрашивает реальный список категорий раздела 'Красота и Спорт' и сравнивает с подготовленным списком")
    @Test
    public void testCatalogBeautyAndSportSection() {
        MainPage mainPage = new MainPage(BASE_URL);
        login(propsHelper.getProperty("UserLogin"), propsHelper.getProperty("UserPassword"));
        mainPage.catalogButtonClick();
        mainPage.beautyAndSportButtonClick();
        List<String> actualBeautyAndSportCategories =
                $$x("//div[@class='category-item mobile-modal opened']//div[@class='subcategory-name hidden-xs hidden-sm']").texts();
        assertThat(actualBeautyAndSportCategories, equalTo(CategoriesData.preparedBeautyAndSportCategories));
    }

    @DisplayName("Тест категории 'Тренажеры и инвентарь'")
    @Description("Запрашивает реальный список подкатегорий категории 'Тренажеры и инвентарь' и сравнивает с подготовленным списком")
    @Test
    public void testExerciseEquipmentCategory(){
        MainPage mainPage = new MainPage(BASE_URL);
        login(propsHelper.getProperty("UserLogin"), propsHelper.getProperty("UserPassword"));
        mainPage.catalogButtonClick();
        mainPage.beautyAndSportButtonClick();
        List<String> actualEquipmentSubCategories =
        $$x("//a[contains(@href, 'trenazhery_i_inventar')]").texts();
        assertThat(actualEquipmentSubCategories, equalTo(CategoriesData.preparedEquipmentSubCategories));
    }
}

