package coffee.shop.ordering.sytem.com.domain.model;

import coffee.shop.ordering.sytem.com.domain.constants.Extra;
import coffee.shop.ordering.sytem.com.domain.constants.OrderStatus;
import coffee.shop.ordering.sytem.com.domain.contract.PriceCalculator;
import coffee.shop.ordering.sytem.com.domain.exception.IllegalOperationException;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

/**
 * Pure formatter. Takes an Order + a PriceCalculator, produces text.
 * Separated from Order because formatting and lifecycle vary along
 * different axes (SRP). If we ever want a JSON or HTML receipt later,
 * we add another formatter — Order doesn't change.
 */
public class Receipt {

  private final Order order;
  private final PriceCalculator calculator;

  public Receipt(Order order, PriceCalculator calculator) {
    this.order = Objects.requireNonNull(order);
    this.calculator = Objects.requireNonNull(calculator);
    if (order.getStatus() == OrderStatus.OPEN) {
      throw new IllegalOperationException("Cannot print a receipt for an unpaid order");
    }
    if (order.getStatus() == OrderStatus.CANCEL) {
      throw new IllegalOperationException("Cannot print a receipt for a cancelled order");
    }
  }

  public String render() {
    StringBuilder sb = new StringBuilder();
    sb.append("=== Receipt #").append(order.getOrderNumber()).append(" ===\n");
    sb.append("Customer: ").append(order.getCustomerName()).append("\n");
    sb.append("--------------------------\n");

    for (Map.Entry<Drink, Integer> entry : order.getDrinks().entrySet()) {
      Drink drink = entry.getKey();
      int qty = entry.getValue();
      BigDecimal lineTotal = calculator.drinkPrice(drink).multiply(BigDecimal.valueOf(qty));

      sb.append(qty).append("x ").append(drink.getDrinkType().getName());
      for (Map.Entry<Extra, Integer> ex : drink.getExtras().entrySet()) {
        sb.append("\n      + ").append(ex.getKey().getName());
        if (ex.getValue() > 1) sb.append(" x").append(ex.getValue());
      }
      sb.append("    $").append(lineTotal).append("\n");
    }

    sb.append("--------------------------\n");
    sb.append("Total:  $").append(calculator.orderPrice(order)).append("\n");
    sb.append("Paid:   $").append(order.getAmountPaid()).append("\n");
    sb.append("Change: $").append(order.change()).append("\n");
    return sb.toString();
  }
}
