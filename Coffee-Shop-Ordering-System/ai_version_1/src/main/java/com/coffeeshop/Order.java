package com.coffeeshop.order;

// Owns the lifecycle. Every state-changing method checks the current status
// and throws if the transition is illegal — no silent no-ops.
//
// Receipt generation is delegated to a separate Receipt class. Order
// computes the total and tracks payment; formatting is not its job.
import com.coffeeshop.drink.Drink;
import com.coffeeshop.money.Money;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Order {
    private final List<Drink> drinks = new ArrayList<>();
    private OrderStatus status = OrderStatus.OPEN;
    private Money amountPaid = Money.zero();

    public void addDrink(Drink drink) {
        if (status != OrderStatus.OPEN) {
            throw new IllegalStateException("Cannot add drinks to a " + status + " order");
        }
        drinks.add(Objects.requireNonNull(drink));
    }

    public Money total() {
        Money total = Money.zero();
        for (Drink d : drinks) total = total.plus(d.price());
        return total;
    }

    public void pay(Money amount) {
        if (status != OrderStatus.OPEN) {
            throw new IllegalStateException("Order is " + status + ", not OPEN");
        }
        if (drinks.isEmpty()) {
            throw new IllegalStateException("Cannot pay for an empty order");
        }
        if (amount.isLessThan(total())) {
            throw new IllegalArgumentException("Insufficient payment");
        }
        this.amountPaid = amount;
        this.status = OrderStatus.PAID;
    }

    public void markReady() {
        if (status != OrderStatus.PAID) {
            throw new IllegalStateException("Order is " + status + ", not PAID");
        }
        this.status = OrderStatus.READY;
    }

    public Money change() {
        if (status == OrderStatus.OPEN) {
            throw new IllegalStateException("Order not paid yet");
        }
        return amountPaid.minus(total());
    }

    public OrderStatus status() { return status; }
    public List<Drink> drinks() { return Collections.unmodifiableList(drinks); }
    public Money amountPaid() { return amountPaid; }
}
