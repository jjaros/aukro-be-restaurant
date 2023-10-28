package src.main.java.cz.jjaros.aukro.restaurant;

import src.main.java.cz.jjaros.aukro.restaurant.dto.OrderedItem;
import src.main.java.cz.jjaros.aukro.restaurant.dto.Table;
import src.main.java.cz.jjaros.aukro.restaurant.dto.menu.FoodOffer;
import src.main.java.cz.jjaros.aukro.restaurant.dto.menu.MenuItem;
import src.main.java.cz.jjaros.aukro.restaurant.service.CashRegister;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Restaurant {

    private static List<MenuItem> createMenu() {
        System.out.println("Creating the menu...");
        return Stream.of(
                new MenuItem(FoodOffer.CRISPY_CHICKEN_SMALL, BigDecimal.valueOf(99), 10),
                new MenuItem(FoodOffer.CRISPY_CHICKEN_BIG, BigDecimal.valueOf(135), 5),
                new MenuItem(FoodOffer.REDBULL, BigDecimal.valueOf(45), 200)
        ).collect(Collectors.toUnmodifiableList());
    }

    public static void main(String[] args) {
        List<MenuItem> dailyMenu = createMenu();
        CashRegister cashRegister = new CashRegister(dailyMenu);

        // order#1
        OrderedItem order1SmallChicken = cashRegister.orderItem(Table.TABLE_1, FoodOffer.CRISPY_CHICKEN_SMALL);
        order1SmallChicken.serve();
        order1SmallChicken.consume();

        OrderedItem order1Redbull = cashRegister.orderItem(Table.TABLE_1, FoodOffer.REDBULL);
        order1Redbull.serve();
        order1Redbull.consume();

        cashRegister.calculateReceipt(Table.TABLE_1);


        // order#2
        OrderedItem order2BigChicken1 = cashRegister.orderItem(Table.TABLE_2, FoodOffer.CRISPY_CHICKEN_BIG);
        order2BigChicken1.serve();
        cashRegister.returnBackToWaiter(Table.TABLE_2, order2BigChicken1);

        OrderedItem order2BigChicken2 = cashRegister.orderItem(Table.TABLE_2, FoodOffer.CRISPY_CHICKEN_BIG);
        order2BigChicken2.serve();
        order2BigChicken2.consume();

        cashRegister.calculateReceipt(Table.TABLE_2);


        // order#3
        OrderedItem order3Redbull = cashRegister.orderItem(Table.TABLE_1, FoodOffer.REDBULL);
        cashRegister.returnBackToWaiter(Table.TABLE_1, order3Redbull);

        cashRegister.calculateReceipt(Table.TABLE_1);


        // shift ends, restaurant closes...
        cashRegister.doInventoryAndClose();
    }
}
