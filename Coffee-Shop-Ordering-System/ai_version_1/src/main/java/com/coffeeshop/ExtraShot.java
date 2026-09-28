package com.coffeeshop.drink.customizations;

import com.coffeeshop.drink.Customization;
import com.coffeeshop.money.Money;

public final class ExtraShot implements Customization {
    public Money priceDelta() { return Money.of("0.80"); }
    public String describe()  { return "Extra shot"; }
}
