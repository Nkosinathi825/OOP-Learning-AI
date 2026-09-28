package com.coffeeshop.menu;

// Identity only. No price here — pricing is policy and policy belongs in
// the price book, not on the menu data. If we later want a happy hour or
// regional pricing, DrinkType doesn't need to change.
public enum DrinkType {
    ESPRESSO, LATTE, CAPPUCCINO, AMERICANO
}
