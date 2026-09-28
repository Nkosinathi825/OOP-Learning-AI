package coffee.shop.ordering.system.com.model.enums;

import java.math.BigDecimal;

public enum DrinkExtras {
  // Milk swaps
  OAT_MILK("0.50"),
  ALMOND_MILK("0.50"),
  SOY_MILK("0.50"),
  WHOLE_MILK("0.00"),

  // Shots & strength
  EXTRA_ESPRESSO_SHOT("0.75"),
  DECAF_SHOT("0.30"),
  RISTRETTO_SHOT("0.40"),

  // Syrups & flavors
  VANILLA_SYRUP("0.60"),
  CARAMEL_SYRUP("0.60"),
  HAZELNUT_SYRUP("0.60"),
  SUGAR_FREE_VANILLA("0.65"),

  // Toppings
  WHIPPED_CREAM("0.50"),
  CINNAMON("0.20"),
  COCOA_POWDER("0.25"),
  CARAMEL_DRIZZLE("0.55"),

  // Temperature / form
  ICED("0.30"),
  EXTRA_HOT("0.00");

  private final BigDecimal price;

  DrinkExtras(String price) {
    this.price = new BigDecimal(price);
  }

  public BigDecimal getPrice() {
    return price;
  }
}
