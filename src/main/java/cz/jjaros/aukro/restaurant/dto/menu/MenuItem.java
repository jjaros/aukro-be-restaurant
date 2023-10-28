package src.main.java.cz.jjaros.aukro.restaurant.dto.menu;

import java.math.BigDecimal;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

public class MenuItem {
    private final FoodOffer foodOffer;
    private final BigDecimal price;
    private int stockAmount;

    public MenuItem(FoodOffer foodOffer, BigDecimal price, int stockAmount) {
        ofNullable(foodOffer)
                .orElseThrow(() -> new IllegalArgumentException("Unable to create menu item without its description!"));
        ofNullable(price)
                .filter(p -> p.signum() > 0)
                .orElseThrow(() -> new IllegalArgumentException("Unable to create menu item with price " + price + " !"));

        this.foodOffer = foodOffer;
        this.price = price;
        validateAndSetStockAvailability(stockAmount);
    }

    public FoodOffer getFood() {
        return foodOffer;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void takeOneFromStock() {
        System.out.println("Taking the menu item " + foodOffer + " from the stock.");
        validateAndSetStockAvailability(--stockAmount);
    }

    public void returnOneToStock() {
        validateAndSetStockAvailability(++stockAmount);
        System.out.println(foodOffer + " returned back to the stock.");
    }

    private void validateAndSetStockAvailability(int newStockAmount) {
        of(newStockAmount)
                .filter(amount -> amount > 0)
                .orElseThrow(() -> new IllegalArgumentException("Unable to create menu item due to insufficient stocks " + newStockAmount + " !"));
        this.stockAmount = newStockAmount;
    }

}
