package coffee.shop.ordering.system.com.domain;

import coffee.shop.ordering.system.com.domain.constants.DrinkType;
import coffee.shop.ordering.system.com.domain.constants.Extra;
import coffee.shop.ordering.system.com.domain.model.Drink;
import coffee.shop.ordering.system.com.domain.model.Menu;
import coffee.shop.ordering.system.com.domain.model.Order;
import coffee.shop.ordering.system.com.domain.model.StandardCalculator;

import java.math.BigDecimal;
import java.util.Map;

public class Main {

  public static void main(String[] args) {

    Map<DrinkType, BigDecimal> drinkPrices = Map.of(
            DrinkType.ESPRESSO, new BigDecimal("2.50"),
            DrinkType.LATTE, new BigDecimal("3.75"),
            DrinkType.CAPPUCCINO, new BigDecimal("3.50"),
            DrinkType.AMERICANO, new BigDecimal("2.80")
    );
    Map<Extra, BigDecimal> extraPrices = Map.of(
            Extra.EXTRA_SHOT, new BigDecimal("0.80"),
            Extra.OAT_MILK, new BigDecimal("0.70"),
            Extra.ALMOND_MILK, new BigDecimal("0.70"),
            Extra.SOY_MILK, new BigDecimal("0.60"),
            Extra.DECAF, BigDecimal.ZERO,
            Extra.EXTRA_LARGE_SIZE, new BigDecimal("1.20")
    );
    Menu.create(drinkPrices,extraPrices);
    StandardCalculator sc = new StandardCalculator();


    Drink drink = new Drink(DrinkType.LATTE);
    drink.addExtra(Extra.EXTRA_SHOT, 2);
    drink.addExtra(Extra.OAT_MILK, 1);
    drink.addExtra(Extra.EXTRA_LARGE_SIZE, 1);
    System.out.println(sc.drinkPrice(drink));

    Drink drink1 = new Drink(DrinkType.ESPRESSO);
    drink1.addExtra(Extra.EXTRA_SHOT, 2);
    drink1.addExtra(Extra.OAT_MILK, 1);
    System.out.println(sc.drinkPrice(drink1));

    Drink drink2 = new Drink(DrinkType.CAPPUCCINO);
    drink2.addExtra(Extra.ALMOND_MILK, 1);
    drink2.addExtra(Extra.DECAF, 1); // free extra
    drink2.addExtra(Extra.EXTRA_LARGE_SIZE, 1);
    System.out.println(sc.drinkPrice(drink2));

    Order order = new Order("Nkosinathi");
    order.addDrink(drink,1);
    order.addDrink(drink1,1);
    order.addDrink(drink2,1);
    order.advanceOrder();
    order.advanceOrder();
    System.out.println(order.getStatus());
    drink1.addExtra(Extra.EXTRA_SHOT,1);
    System.out.println(sc.orderPrice(order));
    System.out.println(order.toString());


  }

}
