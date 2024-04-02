package test.ui.pageObjects;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import test.ui.model.ItemModel;

import java.time.Duration;
import java.util.*;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.exist;
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
    public List<ItemModel> listOfAddedToBasketItems(Double maxPrice, int count) {
        $x("//input[@name='priceTo']").setValue(Double.toString(maxPrice)).pressEnter();
        sleep(2000);
        ElementsCollection allItems = $$x("//div[@class='catalog-item']");
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
        for (int i = 0; i < count; i++) {
            int randomIndex = random.nextInt(items.size());
            String name = items.get(randomIndex).getName();
            $x("//a[text()='"+name+"']/../../..//a[@class='but_car btn block']").shouldBe(exist, Duration.ofMillis(3000L)).shouldBe(enabled, Duration.ofMillis(3000L)).click(ClickOptions.usingJavaScript());
            Double price =  Double.parseDouble($x("//a[text()='"+name+"']/../../..//div[@class='catalog-item-price']").text().replace(" руб", "").replace(",", "."));
            ItemModel item = ItemModel.builder().name(name).price(price).build();
            randomItems.add(item);
        }
        return randomItems;
    }
}
