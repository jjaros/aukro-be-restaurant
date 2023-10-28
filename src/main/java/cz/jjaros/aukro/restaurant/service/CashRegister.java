package src.main.java.cz.jjaros.aukro.restaurant.service;

import src.main.java.cz.jjaros.aukro.restaurant.dto.OrderedItem;
import src.main.java.cz.jjaros.aukro.restaurant.dto.Table;
import src.main.java.cz.jjaros.aukro.restaurant.dto.menu.FoodOffer;
import src.main.java.cz.jjaros.aukro.restaurant.dto.menu.MenuItem;
import src.main.java.cz.jjaros.aukro.restaurant.service.discount.DiscountManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;
import static java.util.function.Function.identity;

public class CashRegister {

    private final DiscountManager discountManager = new DiscountManager();

    private final Map<FoodOffer, MenuItem> menuItems;
    private final Map<Table, List<OrderedItem>> orderedItemsOnTables = new HashMap<>();
    private BigDecimal revenuePerShift = BigDecimal.ZERO;

    public CashRegister(List<MenuItem> menuItems) {
        Stream.ofNullable(menuItems).findAny().orElseThrow(() -> new IllegalArgumentException("Unable to configure cash register without menu items!"));
        this.menuItems = menuItems.stream().collect(Collectors.toMap(MenuItem::getFood, identity()));

        System.out.println("Cash register configured successfully.");
    }

    public OrderedItem orderItem(Table table, FoodOffer food) {
        MenuItem itemToOrder = getMenuItem(food);
        itemToOrder.takeOneFromStock();

        OrderedItem orderedItem = new OrderedItem(itemToOrder.getFood());
        getOrderedItemsOnTable(table).add(orderedItem);
        System.out.println("The menu item " + itemToOrder.getFood() + " has been ordered. Waiting to be served...");
        return orderedItem;
    }

    public void returnBackToWaiter(Table table, OrderedItem itemToReturn) {
        itemToReturn.storno();
        getOrderedItemsOnTable(table).remove(itemToReturn);
        getMenuItem(itemToReturn.getFood()).returnOneToStock();
    }

    public BigDecimal calculateReceipt(Table table) {
        BigDecimal sum = sumOrderedItems(
                orderedItemsOnTables.remove(table)
        );
        revenuePerShift = revenuePerShift.add(sum);
        return sum;
    }

    public void doInventoryAndClose() {
        if (orderedItemsOnTables.isEmpty()) {
            System.out.println("Shift ends, restaurant closes - revenue for today: " + revenuePerShift + "Kc");
        } else {
            BigDecimal todaysLoss = sumOrderedItems(orderedItemsOnTables.values().stream().flatMap(Collection::stream).collect(Collectors.toList()));
            System.out.println("BEWARE: There are unpaid orders. Today's loss: " + todaysLoss + "Kc");
        }
    }

    private MenuItem getMenuItem(FoodOffer food) {
        return ofNullable(menuItems.get(food))
                .orElseThrow(() -> new IllegalArgumentException(food + " is not on the menu today!"));
    }

    private BigDecimal sumOrderedItems(List<OrderedItem> orderedItems) {
        Function<OrderedItem, BigDecimal> calculatePrice = orderedItem -> {
            MenuItem menuItem = getMenuItem(orderedItem.getFood());
            BigDecimal discount = discountManager
                    .calculateDiscount(menuItem, orderedItem.getOrderedAt())
                    .min(BigDecimal.ZERO);
            return menuItem.getPrice().subtract(discount);
        };

        return Stream.ofNullable(orderedItems)
                .flatMap(Collection::stream)
                .map(calculatePrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<OrderedItem> getOrderedItemsOnTable(Table table) {
        if (!orderedItemsOnTables.containsKey(table)) {
            orderedItemsOnTables.put(table, new ArrayList<>());
        }
        return orderedItemsOnTables.get(table);
    }
}
