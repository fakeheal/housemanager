package edu.nbu.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface WorksWithMoney {

    default Integer convertToCents(Float amount) {
        return new BigDecimal(amount)
                .multiply(new BigDecimal(100))
                .setScale(0, RoundingMode.HALF_UP)
                .intValue();
    }

    default Float convertToLeva(Integer amountInCents) {
        return new BigDecimal(amountInCents)
                .divide(new BigDecimal(100), 2, RoundingMode.HALF_UP)
                .floatValue();
    }
}
