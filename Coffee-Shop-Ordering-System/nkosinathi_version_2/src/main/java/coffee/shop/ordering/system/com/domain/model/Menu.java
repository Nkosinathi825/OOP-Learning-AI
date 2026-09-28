package coffee.shop.ordering.system.com.domain.model;

import coffee.shop.ordering.system.com.domain.constants.DrinkType;
import coffee.shop.ordering.system.com.domain.constants.Extra;
import coffee.shop.ordering.system.com.domain.exception.IllegalOperationException;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

public final class Menu {

  private final Map<DrinkType, BigDecimal> drinkPrices;

  private final Map<Extra, BigDecimal> extraPrices;

  private static Menu menu;

  private Menu(Map<DrinkType, BigDecimal> drinkPrices, Map<Extra, BigDecimal> extraPrices) {
    this.drinkPrices = drinkPrices;
    this.extraPrices = extraPrices;
  }

  public static void create(Map<DrinkType, BigDecimal> drinkPrices,
                            Map<Extra, BigDecimal> extraPrices) {
    if (menu != null)
      throw new IllegalOperationException("Can only change menu once a day ");
    menu = new Menu(drinkPrices, extraPrices);
  }

  public static Menu getMenu() {
    if (menu == null)
      throw new IllegalOperationException("The menu hasnt been created yet");
    return menu;
  }

  public Optional<BigDecimal> getDrinkPrice(DrinkType drinkType) {
    return Optional.ofNullable(drinkPrices.get(drinkType));
  }

  public Optional<BigDecimal> getExtraPrice(Extra extra) {
    return Optional.ofNullable(extraPrices.get(extra));
  }

}
