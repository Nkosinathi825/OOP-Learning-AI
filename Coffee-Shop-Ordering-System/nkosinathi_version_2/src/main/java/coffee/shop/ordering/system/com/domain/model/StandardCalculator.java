package coffee.shop.ordering.system.com.domain.model;

import coffee.shop.ordering.system.com.domain.constants.DrinkType;
import coffee.shop.ordering.system.com.domain.constants.Extra;
import coffee.shop.ordering.system.com.domain.contract.PriceCalculator;

import java.math.BigDecimal;
import java.util.Map;

public class StandardCalculator implements PriceCalculator {

  private static final Menu menu = Menu.getMenu();

  @Override
  public BigDecimal drinkPrice(Drink drink) {
    BigDecimal totalPrice = menu.getDrinkPrice(DrinkType.valueOf(drink.getDrinkName())).orElseThrow(
            () -> new IllegalArgumentException("The is no such drink on the menu"));
    totalPrice.add(drink.getExtras().entrySet().stream().
            reduce(new BigDecimal("0"),
                    (a, b) -> a.add(drinkPriceHelper(b)), BigDecimal::add));
    return totalPrice;
  }

  private BigDecimal drinkPriceHelper(Map.Entry<Extra, Integer> extra) {
    BigDecimal extraPrice = menu.getExtraPrice(extra.getKey()).orElseThrow(
            () -> new IllegalArgumentException("The is no such extra on the menu"));
    return extraPrice.multiply(new BigDecimal(String.valueOf(extra.getValue())));
  }

  @Override
  public BigDecimal orderPrice(Order order) {
    return order.getDrinks().entrySet().stream().reduce(new BigDecimal("0"),
            (a, b) -> a.add(orderPriceHelper(b)), BigDecimal::add);
  }

  private BigDecimal orderPriceHelper(Map.Entry<Drink, Integer> drink) {
    return drinkPrice(drink.getKey()).multiply(new BigDecimal(String.valueOf(drink.getValue())));
  }
}
