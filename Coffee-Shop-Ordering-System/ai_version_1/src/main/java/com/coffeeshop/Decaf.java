package com.coffeeshop.drink.customizations;

import com.coffeeshop.drink.Customization;
import com.coffeeshop.money.Money;

public final class Decaf implements Customization {
    public Money priceDelta() { return Money.zero(); }
    public String describe()  { return "Decaf"; }
}
