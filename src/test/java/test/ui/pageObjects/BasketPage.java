package test.ui.pageObjects;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import test.ui.model.ItemModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.codeborne.selenide.Selenide.$$x;

public class BasketPage {
    Random random = new Random();

    public List<ItemModel> listOfActualInBasketItems() {
        ElementsCollection actualInBasketItems = $$x("//main[@class='content flx-main flx-m-12']//div[@class='cart-item flex valign-center']");
        List<ItemModel> itemModelsList = new ArrayList<>();
        for (SelenideElement item : actualInBasketItems) {
            String name = item.$x(".//div[@class='cart-item-product-title']").text();
            Double price = Double.parseDouble(item.$x(".//div[@class='cart-item-price']")
                    .getText().replace(" руб", "").replace(",", "."));
            ItemModel model = ItemModel.builder().name(name).price(price).build();
            itemModelsList.add(model);
        }
        return itemModelsList;
    }

    public List<ItemModel> listOfAddedToBasketItems(Double maxPrice, int count) {
        ElementsCollection allItems = $$x("//div[@class='catalog-item']");
        List<ItemModel> itemModelsList = new ArrayList<>();
        List<SelenideElement> belowPriceItems = new ArrayList<>();
        for (SelenideElement item : allItems) {
            double itemPrice = Double.parseDouble(item.$x(".//div[@class='catalog-item-price']").getText().replace(" руб", "").replace(",", "."));
            if (itemPrice <= maxPrice) {
                belowPriceItems.add(item);
            }
        }
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

