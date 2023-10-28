package src.main.java.cz.jjaros.aukro.restaurant.service.discount;

import src.main.java.cz.jjaros.aukro.restaurant.dto.menu.MenuItem;

import java.math.BigDecimal;

@FunctionalInterface
public interface DiscountCalculator {

    /**
     * Calculates the discount value.
     *
     * @param basePrice menu item price
     * @param discount discount value
     * @return discount value
     */
    BigDecimal calculateDiscountValue(BigDecimal basePrice, BigDecimal discount);
}
