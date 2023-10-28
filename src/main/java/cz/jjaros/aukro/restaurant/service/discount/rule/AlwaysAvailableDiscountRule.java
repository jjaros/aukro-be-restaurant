package src.main.java.cz.jjaros.aukro.restaurant.service.discount.rule;

import src.main.java.cz.jjaros.aukro.restaurant.dto.menu.MenuItem;

import java.time.LocalDateTime;

/**
 * Validation rule for discounts to be available at any time.
 */
public class AlwaysAvailableDiscountRule implements DiscountRule {

    @Override
    public boolean isValid(MenuItem menuItem, LocalDateTime orderedAt) {
        return true;
    }
}
