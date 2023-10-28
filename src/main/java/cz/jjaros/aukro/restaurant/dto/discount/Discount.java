package src.main.java.cz.jjaros.aukro.restaurant.dto.discount;

import src.main.java.cz.jjaros.aukro.restaurant.service.discount.DiscountCalculator;
import src.main.java.cz.jjaros.aukro.restaurant.service.discount.rule.DiscountRule;

import java.math.BigDecimal;
import java.util.Optional;

import static java.util.Optional.ofNullable;

public class Discount {

    private final DiscountType discountType;
    private final BigDecimal discountValue;
    private final String description;
    private final DiscountRule validationRule;

    public Discount(DiscountType discountType, BigDecimal discountValue, DiscountRule validationRule, String description) {
        ofNullable(discountType).orElseThrow(() -> new IllegalArgumentException("Invalid discount type."));
        ofNullable(discountValue)
                .filter(d -> d.signum() > 0)
                .orElseThrow(() -> new IllegalArgumentException("Discount must be greater than 0."));

        this.discountType = discountType;
        this.discountValue = discountValue;
        this.description = description;
        this.validationRule = validationRule;
    }

    public DiscountCalculator getCalculator() {
        return discountType.calculator();
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public String getDescription() {
        return description;
    }

    public Optional<DiscountRule> getValidationRule() {
        return ofNullable(validationRule);
    }
}
