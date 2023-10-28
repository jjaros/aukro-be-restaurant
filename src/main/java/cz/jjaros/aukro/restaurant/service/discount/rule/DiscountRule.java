package src.main.java.cz.jjaros.aukro.restaurant.service.discount.rule;

import src.main.java.cz.jjaros.aukro.restaurant.dto.menu.MenuItem;

import java.time.LocalDateTime;

/**
 * Validation rule to specify when the discount should be available.
 * The {@link DiscountRule#isValid(MenuItem, LocalDateTime)} method is used for this purpose.
 */
public interface DiscountRule {

    boolean isValid(MenuItem menuItem, LocalDateTime orderedAt);
}
