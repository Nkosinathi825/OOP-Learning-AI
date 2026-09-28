package com.coffeeshop.drink.customizations;

import com.coffeeshop.drink.Customization;
import com.coffeeshop.money.Money;

public final class ExtraLarge implements Customization {
    public Money priceDelta() { return Money.of("1.00"); }
    public String describe()  { return "Extra large"; }
}
