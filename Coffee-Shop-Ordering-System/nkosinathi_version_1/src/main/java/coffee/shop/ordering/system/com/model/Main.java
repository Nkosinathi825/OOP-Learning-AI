package coffee.shop.ordering.system.com.model;

import coffee.shop.ordering.system.com.model.enums.DrinkExtras;
import coffee.shop.ordering.system.com.model.enums.DrinkType;

public class Main {

  public static void main(String[] args) {
    Drink drink = new Drink(DrinkType.DRIP_COFFEE);
    drink.addExtra(DrinkExtras.EXTRA_HOT,1);
    drink.addExtra(DrinkExtras.ALMOND_MILK,5);
    Order order = new Order(new Drink(DrinkType.AMERICANO));
    order.addDrinks(drink,1);
    order.confirmOrder();
    System.out.println(order.print());
  }
}
