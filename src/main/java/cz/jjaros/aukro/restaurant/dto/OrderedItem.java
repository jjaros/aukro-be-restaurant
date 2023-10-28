package src.main.java.cz.jjaros.aukro.restaurant.dto;

import src.main.java.cz.jjaros.aukro.restaurant.dto.menu.FoodOffer;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

import static java.util.Optional.ofNullable;

public class OrderedItem {
    private final UUID id;
    private final FoodOffer foodOffer;
    private final LocalDateTime orderedAt;
    private boolean served;
    private boolean consumed;

    public OrderedItem(FoodOffer foodOffer) {
        ofNullable(foodOffer).orElseThrow(() -> new NoSuchElementException("No item to order!"));

        this.id = UUID.randomUUID();
        this.foodOffer = foodOffer;
        this.orderedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public FoodOffer getFood() {
        return foodOffer;
    }

    public LocalDateTime getOrderedAt() {
        return orderedAt;
    }

    public void serve() {
        served = true;
        System.out.println(foodOffer + " is served.");
    }

    public void consume() {
        if (!served) {
            throw new IllegalStateException("You cannot consume food that has not yet been served!");
        }
        consumed = true;
        System.out.println(foodOffer + " is consumed.");
    }

    public void storno() {
        System.out.println(foodOffer + " is being cancelled.");
        if (consumed) {
            throw new IllegalStateException("You cannot return already consumed food.");
        }
        served = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderedItem)) return false;
        OrderedItem that = (OrderedItem) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
