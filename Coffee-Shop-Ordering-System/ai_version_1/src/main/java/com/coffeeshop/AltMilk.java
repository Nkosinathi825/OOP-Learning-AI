package com.coffeeshop.drink.customizations;

import com.coffeeshop.drink.Customization;
import com.coffeeshop.money.Money;

import java.util.Objects;

public final class AltMilk implements Customization {
    public enum Kind { OAT, ALMOND, SOY }
    private final Kind kind;
    public AltMilk(Kind kind) { this.kind = Objects.requireNonNull(kind); }
    public Money priceDelta() { return Money.of("0.60"); }
    public String describe()  { return kind.name().toLowerCase() + " milk"; }
    public Kind kind() { return kind; }
}
