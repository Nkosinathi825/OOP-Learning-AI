package com.coffeeshop.order;

// Pure formatting. Takes an order, produces text. Separated from Order
// because rendering and lifecycle are different responsibilities (SRP),
// and because we may later want HTML / JSON receipts without touching Order.
import com.coffeeshop.drink.Customization;
import com.coffeeshop.drink.Drink;

public final class Receipt {
    private final Order order;

    public Receipt(Order order) {
        if (order.status() == OrderStatus.OPEN) {
            throw new IllegalStateException("Cannot print receipt for unpaid order");
        }
        this.order = order;
    }

    public String render() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Receipt ===\n");
        for (Drink d : order.drinks()) {
            sb.append(d.type().name());
            for (Customization c : d.customizations()) {
                sb.append(" + ").append(c.describe());
            }
            sb.append(" .......... ").append(d.price()).append("\n");
        }
        sb.append("Total:  ").append(order.total()).append("\n");
        sb.append("Paid:   ").append(order.amountPaid()).append("\n");
        sb.append("Change: ").append(order.change()).append("\n");
        return sb.toString();
    }
}
