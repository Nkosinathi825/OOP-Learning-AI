package coffee.shop.ordering.sytem.com.domain.model;

import coffee.shop.ordering.sytem.com.domain.constants.DrinkType;
import coffee.shop.ordering.sytem.com.domain.constants.Extra;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/**
 * Plain value-ish object. No more singleton, no more global state.
 * Construct it once at startup, pass it to whoever needs it (DIP).
 *
 * Defensive copies into EnumMap on construction so callers can't mutate
 * the menu after the fact through the map references they passed in.
 *
 * Optional was removed from the getters — every caller treated absence
 * as an error, so the lookup throws directly. Cleaner API, no
 * performative wrappers (YAGNI).
 */
public final class Menu {

  private final Map<DrinkType, BigDecimal> drinkPrices;
  private final Map<Extra, BigDecimal> extraPrices;

  public Menu(Map<DrinkType, BigDecimal> drinkPrices,
              Map<Extra, BigDecimal> extraPrices) {
    Objects.requireNonNull(drinkPrices);
    Objects.requireNonNull(extraPrices);
    this.drinkPrices = new EnumMap<>(drinkPrices);
    this.extraPrices = new EnumMap<>(extraPrices);
  }

  public BigDecimal getDrinkPrice(DrinkType type) {
    BigDecimal price = drinkPrices.get(type);
    if (price == null) {
      throw new IllegalArgumentException("No price on the menu for " + type);
    }
    return price;
  }

  public BigDecimal getExtraPrice(Extra extra) {
    BigDecimal price = extraPrices.get(extra);
    if (price == null) {
      throw new IllegalArgumentException("No price on the menu for " + extra);
    }
    return price;
  }
}
