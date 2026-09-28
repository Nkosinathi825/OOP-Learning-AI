package com.coffeeshop.menu;

// Pricing policy. Separated from DrinkType so the menu's identity is
// stable while pricing can vary. One implementation is fine for now,
// but the seam is cheap and clean.
import com.coffeeshop.money.Money;

import java.util.EnumMap;
import java.util.Map;

public final class PriceBook {
    private final Map<DrinkType, Money> prices;

    public PriceBook(Map<DrinkType, Money> prices) {
        this.prices = new EnumMap<>(prices);
    }

    public static PriceBook standard() {
        Map<DrinkType, Money> p = new EnumMap<>(DrinkType.class);
        p.put(DrinkType.ESPRESSO,   Money.of("2.50"));
        p.put(DrinkType.LATTE,      Money.of("3.50"));
        p.put(DrinkType.CAPPUCCINO, Money.of("3.25"));
        p.put(DrinkType.AMERICANO,  Money.of("2.75"));
        return new PriceBook(p);
    }

    public Money basePrice(DrinkType type) {
        return prices.get(type);
    }
}
