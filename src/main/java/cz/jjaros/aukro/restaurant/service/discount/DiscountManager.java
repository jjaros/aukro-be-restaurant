package src.main.java.cz.jjaros.aukro.restaurant.service.discount;

import src.main.java.cz.jjaros.aukro.restaurant.dto.discount.Discount;
import src.main.java.cz.jjaros.aukro.restaurant.dto.discount.DiscountType;
import src.main.java.cz.jjaros.aukro.restaurant.dto.menu.FoodOfferType;
import src.main.java.cz.jjaros.aukro.restaurant.dto.menu.MenuItem;
import src.main.java.cz.jjaros.aukro.restaurant.service.discount.rule.AlwaysAvailableDiscountRule;
import src.main.java.cz.jjaros.aukro.restaurant.service.discount.rule.DiscountRule;
import src.main.java.cz.jjaros.aukro.restaurant.service.discount.rule.TimeIntervalDiscountRule;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;

import static java.util.Optional.ofNullable;

public class DiscountManager {

    private final DiscountRule defaultDiscountRule = new AlwaysAvailableDiscountRule();

    private static final Map<FoodOfferType, Discount> AVAILABLE_DISCOUNTS = Map.of(
            FoodOfferType.MAIN_COURSES,
            new Discount(
                    DiscountType.PERCENTAGE, BigDecimal.valueOf(20),
                    new TimeIntervalDiscountRule(LocalTime.of(11, 0), LocalTime.of(13, 0)),
                    "20% between 11:00 - 13:00."
            ),

            FoodOfferType.DRINKS,
            new Discount(
                    DiscountType.ABSOLUTE, BigDecimal.valueOf(10),
                    new TimeIntervalDiscountRule(LocalTime.of(14, 0), LocalTime.of(16, 0)),
                    "10Kc on every drink between 14:00 - 16:00."
            )
    );

    public BigDecimal calculateDiscount(MenuItem menuItem, LocalDateTime orderedAt) {
        Optional<Discount> discountDescriptor = ofNullable(
                AVAILABLE_DISCOUNTS.get(menuItem.getFood().getType())
        );
        return discountDescriptor
                .filter(d -> d.getValidationRule().orElse(defaultDiscountRule).isValid(menuItem, orderedAt))
                .map(d -> d.getCalculator().calculateDiscountValue(menuItem.getPrice(), d.getDiscountValue()))
                .orElse(BigDecimal.ZERO);
    }
}
