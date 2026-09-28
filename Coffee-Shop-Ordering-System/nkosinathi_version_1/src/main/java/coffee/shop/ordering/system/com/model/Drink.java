package coffee.shop.ordering.system.com.model;


import coffee.shop.ordering.system.com.model.enums.DrinkExtras;
import coffee.shop.ordering.system.com.model.enums.DrinkType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Drink {

  private final DrinkType drinkType;

  private final List<DrinkExtras> drinkExtras;

  public Drink(DrinkType drinkType) {
    this.drinkType = drinkType;
    drinkExtras = new ArrayList<>();
  }

  public DrinkType getDrinkType() {
    return drinkType;
  }

  public void addExtra(DrinkExtras drinkExtra, int numberOfExtra) {
    for (int i = 0; i < numberOfExtra; i++) {
      drinkExtras.add(drinkExtra);
    }

  }

  public void removeExtra(DrinkExtras drinkExtra, int numberOfExtra) {
    for (int i = 0; i < numberOfExtra; i++) {
      drinkExtras.remove(drinkExtra);
    }
  }

  public BigDecimal getTotal() {
    BigDecimal totalPrice = new BigDecimal("0");
    for (DrinkExtras drinkExtra : drinkExtras) {
      totalPrice.add(drinkExtra.getPrice());
    }
    totalPrice.add(drinkType.getPrice());
    return totalPrice;
  }

  @Override
  public String toString() {
   return String.format("The order Drink is : %s  price : %s \n " +
                    "Extras : \n :%s price :%s", drinkType, drinkType.getPrice(), drinkExtras,
            drinkExtras.stream().map(drink -> drink.getPrice()).reduce(new BigDecimal("0"),
                    (a, b) -> a.add(b)).doubleValue());

  }
}
