package com.coffeeshop.drink;

// Each customization knows its own price impact and how to describe itself.
// Polymorphism here is doing real work: it lets Drink.price() and
// receipt rendering treat all customizations uniformly without a switch.
import com.coffeeshop.money.Money;

public interface Customization {
    Money priceDelta();
    String describe();
}
