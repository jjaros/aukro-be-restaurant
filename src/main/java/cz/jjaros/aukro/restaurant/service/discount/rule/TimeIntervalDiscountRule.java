package src.main.java.cz.jjaros.aukro.restaurant.service.discount.rule;

import src.main.java.cz.jjaros.aukro.restaurant.dto.menu.MenuItem;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Validation rule for discounts to be available within a certain time interval.
 */
public class TimeIntervalDiscountRule implements DiscountRule {

    private final LocalTime start;
    private final LocalTime end;

    public TimeIntervalDiscountRule(LocalTime start, LocalTime end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean isValid(MenuItem menuItem, LocalDateTime orderedAt) {
        LocalTime orderedTime = orderedAt.toLocalTime();
        return orderedTime.isAfter(start) && orderedTime.isBefore(end);
    }
}
