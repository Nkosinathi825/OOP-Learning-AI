package com.coffeeshop.drink;

// Aggregates a type, the customizations applied, and the price book it was
// priced against. The price book is captured at construction so a drink's
// price is stable once made.
//
// Validation (only one alt-milk) lives here, in the constructor — single
// funnel for invariants. Customizations are defensively copied and exposed
// only as an unmodifiable view. We do NOT hand out the live list.
import com.coffeeshop.drink.customizations.AltMilk;
import com.coffeeshop.menu.DrinkType;
import com.coffeeshop.menu.PriceBook;
import com.coffeeshop.money.Money;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Drink {
    private final DrinkType type;
    private final List<Customization> customizations;
    private final PriceBook priceBook;

    public Drink(DrinkType type, List<Customization> customizations, PriceBook priceBook) {
        this.type = Objects.requireNonNull(type);
        this.priceBook = Objects.requireNonNull(priceBook);
        List<Customization> copy = new ArrayList<>(customizations);
        long altMilkCount = copy.stream().filter(c -> c instanceof AltMilk).count();
        if (altMilkCount > 1) {
            throw new IllegalArgumentException("At most one alt-milk per drink");
        }
        this.customizations = Collections.unmodifiableList(copy);
    }

    public DrinkType type() { return type; }
    public List<Customization> customizations() { return customizations; }

    public Money price() {
        Money total = priceBook.basePrice(type);
        for (Customization c : customizations) {
            total = total.plus(c.priceDelta());
        }
        return total;
    }
}
