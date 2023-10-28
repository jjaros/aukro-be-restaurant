package src.main.java.cz.jjaros.aukro.restaurant.dto.discount;

import src.main.java.cz.jjaros.aukro.restaurant.service.discount.DiscountCalculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public enum DiscountType {

    /**
     * Discount determined by a percentage rate.
     */
    PERCENTAGE(((basePrice, discountValue) -> basePrice.multiply(discountValue).divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP))),

    /**
     * Discount determined by absolute value.
     */
    ABSOLUTE((basePrice, discountValue) -> discountValue);

    private final DiscountCalculator discountCalculatorFunction;

    DiscountType(DiscountCalculator discountCalculatorFunction) {
        this.discountCalculatorFunction = discountCalculatorFunction;
    }

    public DiscountCalculator calculator() {
        return discountCalculatorFunction;
    }
}
