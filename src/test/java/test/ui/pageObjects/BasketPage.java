package test.ui.pageObjects;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import test.ui.model.ItemModel;

import java.time.Duration;
import java.util.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class BasketPage {
    Random random = new Random();
    SelenideElement basketTotalPriceValue = $x("//span[@class='total-clubcard-price summa_car1']");
    SelenideElement priceToFilter = $x("//input[@name='priceTo']");
    ElementsCollection onPageItems = $$x("//div[@class='catalog-item']");

    @Step("Создание списка товаров находящихся в корзине")
    public Set<ItemModel> listOfActualInBasketItems() {
        ElementsCollection actualInBasketItems = $$x("//main[@class='content flx-main flx-m-12']//div[@class='cart-item flex valign-center']");
        Set<ItemModel> itemModelsList = new HashSet<>();
        for (SelenideElement item : actualInBasketItems) {
            String name = item.$x(".//div[@class='cart-item-product-title']").text();
            Double price = Double.parseDouble(item.$x(".//div[@class='cart-item-price']")
                    .getText().replace(" руб", "").replace(",", "."));
            ItemModel model = ItemModel.builder().name(name).price(price).build();
            itemModelsList.add(model);
        }
        return itemModelsList;
    }

    /**
     * Создается ElementCollection всех товаров на страницы и из него создается новая коллекция с подходящей максимальной ценой
     * Выбирается два товара, из имеющих подходящую цену и добавляются в корзину
     */
    @Step("Создание списка из {1} товаров с ценой ниже {0} руб, добавление товаров в корзину")
    public List<ItemModel> addedToBasketItems(Double maxPrice, int count) {
        priceToFilter.setValue(Double.toString(maxPrice)).pressEnter();
        $x("//ul[@style]").shouldBe(exist);
        $x("//ul[@class='list_catalog']").shouldHave(cssValue("opacity", "1"), Duration.ofMillis(5000L));
        ElementsCollection allItems = onPageItems;
        List<ItemModel> items = new ArrayList<>();
        for (SelenideElement item : allItems) {
            String name = item.$x(".//div[@class='catalog-item-title']").text();
            double itemPrice = Double.parseDouble(item.$x(".//div[@class='catalog-item-price']").getText().replace(" руб", "").replace(",", "."));
            System.out.println(name);
            if (itemPrice <= maxPrice) {
                ItemModel model = ItemModel.builder().name(name).price(itemPrice).build();
                items.add(model);
            }
        }
        List<ItemModel> randomItems = new ArrayList<>();
        if (count > items.size()) count = items.size();
        for (int i = 0; i < count; i++) {
            int randomIndex = random.nextInt(items.size());
            String name = items.get(randomIndex).getName();
            $x("//a[text()='"+name+"']/../../..//a[@class='but_car btn block']")
                    .shouldBe(exist, Duration.ofMillis(3000L)).shouldBe(enabled, Duration.ofMillis(3000L)).click(ClickOptions.usingJavaScript());
            $x("//div[@id='basketlabel']").shouldBe(exist);
            $x("//ul[@class='list_catalog']").shouldHave(cssValue("opacity", "1"), Duration.ofMillis(5000L));
            Double price =  Double.parseDouble($x("//a[text()='"+name+"']/../../..//div[@class='catalog-item-price']")
                    .text().replace(" руб", "").replace(",", "."));
            ItemModel item = ItemModel.builder().name(name).price(price).build();
            randomItems.add(item);
        }
        return randomItems;
    }
    @Step("Вычисление общей стоимости добавленных в корзину товаров")
    public double sumOfItems(List<ItemModel> items){
        return items.stream().mapToDouble(ItemModel::getPrice).sum();
    }
    @Step("Получение общей стоимости корзины")
    public double basketTotalPrice(){
        return Double.parseDouble(basketTotalPriceValue
                .text().replace("руб", "").replace("коп.", "").replace(" ", ""));
    }
}
