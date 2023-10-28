package src.main.java.cz.jjaros.aukro.restaurant.dto.menu;

/**
 * Offer of drinks and dishes that can be used in the menu.
 */
public enum FoodOffer {

    REDBULL(FoodOfferType.DRINKS, "Red Bull - Gives You Wings"),
    CRISPY_CHICKEN_BIG(FoodOfferType.MAIN_COURSES, "120g - The crispiest chicken you've ever eaten! Fries with it."),
    CRISPY_CHICKEN_SMALL(FoodOfferType.MAIN_COURSES, "70g - The crispiest chicken you've ever eaten! Fries with it.");

    private final FoodOfferType type;
    private final String description;

    FoodOffer(FoodOfferType type, String description) {
        this.type = type;
        this.description = description;
    }

    public FoodOfferType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
