package coffee.shop.ordering.sytem.com.domain.model;

import coffee.shop.ordering.sytem.com.domain.constants.OrderStatus;
import coffee.shop.ordering.sytem.com.domain.contract.PriceCalculator;
import coffee.shop.ordering.sytem.com.domain.exception.IllegalOperationException;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Owns the order's lifecycle and the act of transitioning between states.
 * Asks OrderStatus whether a transition is structurally legal, then
 * applies the data-side preconditions (e.g. enough money to pay).
 *
 * Calculator is injected (DIP) — Order uses it to compute totals when
 * needed. No more `new StandardCalculator()` constructed inside toString.
 *
 * Transition methods are named (pay / cancel / markReady) instead of a
 * generic advance(). The caller's intent is explicit at the call site.
 *
 * Methods that used to return boolean-and-throw now return void —
 * boolean was lying about a "false" outcome that never happened.
 */
public class Order {

  private static long orderCounter = 0;          // was instance field; every Order had id 0

  private final long orderNumber;
  private final String customerName;
  private final Map<Drink, Integer> drinks;
  private final PriceCalculator calculator;

  private OrderStatus status;
  private BigDecimal amountPaid = BigDecimal.ZERO;

  public Order(String customerName, PriceCalculator calculator) {
    this.orderNumber = ++orderCounter;
    this.customerName = Objects.requireNonNull(customerName);
    this.calculator = Objects.requireNonNull(calculator);
    this.drinks = new HashMap<>();
    this.status = OrderStatus.OPEN;
  }

  public void addDrink(Drink drink, int quantity) {
    requireOpen();
    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity must be positive");
    }
    Objects.requireNonNull(drink);
    Drink snapshot = new Drink(drink);            // defensive copy fixes aliasing
    drinks.merge(snapshot, quantity, Integer::sum);
  }

  public void removeDrink(Drink drink, int quantity) {
    requireOpen();
    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity must be positive");
    }
    Integer current = drinks.get(drink);
    if (current == null || current < quantity) { // was <=, off-by-one
      throw new IllegalOperationException(
              "Cannot remove " + quantity + " of " + drink + " — only " +
              (current == null ? 0 : current) + " present");
    }
    int remaining = current - quantity;
    if (remaining == 0) drinks.remove(drink);
    else drinks.put(drink, remaining);
  }

  public BigDecimal total() {
    return calculator.orderPrice(this);
  }

  public void pay(BigDecimal amount) {
    if (!status.canTransitionTo(OrderStatus.PAID)) {
      throw new IllegalOperationException("Cannot pay an order in state " + status);
    }
    if (drinks.isEmpty()) {
      throw new IllegalOperationException("Cannot pay for an empty order");
    }
    Objects.requireNonNull(amount);
    BigDecimal total = total();
    if (amount.compareTo(total) < 0) {
      throw new IllegalArgumentException(
              "Insufficient payment: " + amount + " < total " + total);
    }
    this.amountPaid = amount;
    this.status = OrderStatus.PAID;
  }

  public void markReady() {
    if (!status.canTransitionTo(OrderStatus.READY)) {
      throw new IllegalOperationException("Cannot mark ready an order in state " + status);
    }
    this.status = OrderStatus.READY;
  }

  public void cancel() {
    if (!status.canTransitionTo(OrderStatus.CANCEL)) {
      throw new IllegalOperationException("Cannot cancel an order in state " + status);
    }
    this.status = OrderStatus.CANCEL;
  }

  public BigDecimal change() {
    if (status == OrderStatus.OPEN || status == OrderStatus.CANCEL) {
      throw new IllegalOperationException("No change available for an order in state " + status);
    }
    return amountPaid.subtract(total());
  }

  private void requireOpen() {
    if (status != OrderStatus.OPEN) {
      throw new IllegalOperationException("Order is " + status + ", no longer open for changes");
    }
  }

  public long getOrderNumber()   { return orderNumber; }
  public String getCustomerName(){ return customerName; }
  public OrderStatus getStatus() { return status; }
  public BigDecimal getAmountPaid() { return amountPaid; }
  public Map<Drink, Integer> getDrinks() { return Collections.unmodifiableMap(drinks); }

  @Override
  public String toString() {
    // Plain debug summary — NOT a receipt. Receipt is its own class.
    return "Order#" + orderNumber + " [" + customerName + ", " +
            status + ", " + drinks.size() + " line items]";
  }
}
