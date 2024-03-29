package test.ui.pageObjects;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import test.ui.model.ItemModel;

import java.util.*;

import static com.codeborne.selenide.Selenide.*;

public class BasketPage {
    Random random = new Random();

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
    public Set<ItemModel> listOfAddedToBasketItems(Double maxPrice, int count) {
        $x("//input[@name='priceTo']").setValue(Double.toString(maxPrice)).pressEnter();
        sleep(2000);
        ElementsCollection allItems = $$x("//div[@class='catalog-item']");
        List<SelenideElement> belowPriceItems = new ArrayList<>();
        for (SelenideElement item : allItems) {
            double itemPrice = Double.parseDouble(item.$x(".//div[@class='catalog-item-price']").getText().replace(" руб", "").replace(",", "."));
            if (itemPrice <= maxPrice) {
                belowPriceItems.add(item);
            }
        }

        Set<ItemModel> itemModelsList = new HashSet<>();
        for (int i = 0; i < count; i++) {
            int randomIndex = random.nextInt(belowPriceItems.size());
            SelenideElement randomElement = belowPriceItems.get(randomIndex);
            randomElement.$x(".//a[@class='but_car btn block']").click();
            String name = randomElement.$x(".//div[@class='catalog-item-title']").text();
            Double price = Double.parseDouble(randomElement.$x(".//div[@class='catalog-item-price']").getText().replace(" руб", "").replace(",", "."));
            ItemModel model = ItemModel.builder().name(name).price(price).build();
            itemModelsList.add(model);
        }

        return itemModelsList;
    }



}

