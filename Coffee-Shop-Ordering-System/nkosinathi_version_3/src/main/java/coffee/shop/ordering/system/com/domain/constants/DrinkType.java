package coffee.shop.ordering.sytem.com.domain.constants;

public enum DrinkType {

  ESPRESSO("espresso"),
  LATTE("latte"),
  CAPPUCCINO("cappuccino"),
  AMERICANO("americano");

  private final String name;

  DrinkType(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }
}
