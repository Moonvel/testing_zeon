package test.ui.tests;

import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test.ui.datagenerator.CategoriesData;
import test.ui.pageObjects.MainPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CatalogSectionsTests extends TestBase {


    @DisplayName("Тест раздела 'Компьютеры и сети'")
    @Description("Запрашивает реальный список категорий раздела 'Компьютеры и сети' и сравнивает с подготовленным списком")
    @Test
    public void testComputersAndNetWork() {
        MainPage mainPage = new MainPage();
        mainPage.catalogButtonClick();
        mainPage.catalogCategoryButtonClick("Компьютеры и сети");
        assertThat(mainPage.actualCategories.texts(), equalTo(CategoriesData.preparedComputersAndNetworkCategories));
    }

    @DisplayName("Тест раздела 'Электроника'")
    @Description("Запрашивает реальный список категорий раздела 'Электроника' и сравнивает с подготовленным списком")
    @Test
    public void testCatalogElectronics() {
        MainPage mainPage = new MainPage();
        mainPage.catalogButtonClick();
        mainPage.catalogCategoryButtonClick("Электроника");
        assertThat(mainPage.actualCategories.texts(), equalTo(CategoriesData.preparedElectronicsCategories));
    }

    @DisplayName("Тест раздела 'Красота и Спорт'")
    @Description("Запрашивает реальный список категорий раздела 'Красота и Спорт' и сравнивает с подготовленным списком")
    @Test
    public void testCatalogBeautyAndSportSection() {
        MainPage mainPage = new MainPage();
        mainPage.catalogButtonClick();
        mainPage.catalogCategoryButtonClick("Красота и спорт");
        assertThat(mainPage.actualCategories.texts(), equalTo(CategoriesData.preparedBeautyAndSportCategories));
    }

    @DisplayName("Тест категории 'Тренажеры и инвентарь'")
    @Description("Запрашивает реальный список подкатегорий 'Тренажеры и инвентарь' в категории 'Красота и спорт' и сравнивает с подготовленным списком")
    @Test
    public void testExerciseEquipmentCategory(){
        MainPage mainPage = new MainPage();
        mainPage.catalogButtonClick();
        mainPage.catalogCategoryButtonClick("Красота и спорт");
        assertThat(mainPage.actualSubCategories.texts(), equalTo(CategoriesData.preparedEquipmentSubCategories));
    }
}

