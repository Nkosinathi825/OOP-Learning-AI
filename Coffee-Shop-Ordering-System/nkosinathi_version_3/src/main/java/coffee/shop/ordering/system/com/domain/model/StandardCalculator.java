package coffee.shop.ordering.sytem.com.domain.model;

import coffee.shop.ordering.sytem.com.domain.contract.PriceCalculator;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

/**
 * Menu is injected through the constructor (DIP). No more static
 * dependence on Menu.getMenu() — that hid a singleton coupling and
 * destroyed test isolation.
 *
 * The big bug: BigDecimal is immutable. `totalPrice.add(...)` returns
 * a new value; calling .add and ignoring the return changes nothing.
 * Now every accumulation reassigns the result.
 */
public class StandardCalculator implements PriceCalculator {

  private final Menu menu;

  public StandardCalculator(Menu menu) {
    this.menu = Objects.requireNonNull(menu);
  }

  @Override
  public BigDecimal drinkPrice(Drink drink) {
    Objects.requireNonNull(drink);
    BigDecimal total = menu.getDrinkPrice(drink.getDrinkType());
    for (Map.Entry<coffee.shop.ordering.sytem.com.domain.constants.Extra, Integer> e
            : drink.getExtras().entrySet()) {
      BigDecimal extraPrice = menu.getExtraPrice(e.getKey());
      total = total.add(extraPrice.multiply(BigDecimal.valueOf(e.getValue())));
    }
    return total;
  }

  @Override
  public BigDecimal orderPrice(Order order) {
    Objects.requireNonNull(order);
    BigDecimal total = BigDecimal.ZERO;
    for (Map.Entry<Drink, Integer> e : order.getDrinks().entrySet()) {
      BigDecimal lineTotal = drinkPrice(e.getKey()).multiply(BigDecimal.valueOf(e.getValue()));
      total = total.add(lineTotal);
    }
    return total;
  }
}
