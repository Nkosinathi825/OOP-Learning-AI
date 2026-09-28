package coffee.shop.ordering.sytem.com.domain;

import coffee.shop.ordering.sytem.com.domain.constants.DrinkType;
import coffee.shop.ordering.sytem.com.domain.constants.Extra;
import coffee.shop.ordering.sytem.com.domain.model.*;

import java.math.BigDecimal;
import java.util.Map;

public class Main {

  public static void main(String[] args) {

    Map<DrinkType, BigDecimal> drinkPrices = Map.of(
            DrinkType.ESPRESSO,   new BigDecimal("2.50"),
            DrinkType.LATTE,      new BigDecimal("3.75"),
            DrinkType.CAPPUCCINO, new BigDecimal("3.50"),
            DrinkType.AMERICANO,  new BigDecimal("2.80"));

    Map<Extra, BigDecimal> extraPrices = Map.of(
            Extra.EXTRA_SHOT,        new BigDecimal("0.80"),
            Extra.OAT_MILK,          new BigDecimal("0.70"),
            Extra.ALMOND_MILK,       new BigDecimal("0.70"),
            Extra.SOY_MILK,          new BigDecimal("0.60"),
            Extra.DECAF,             BigDecimal.ZERO,
            Extra.EXTRA_LARGE_SIZE,  new BigDecimal("1.20"));

    Menu menu = new Menu(drinkPrices, extraPrices);
    StandardCalculator calc = new StandardCalculator(menu);

    Drink latte = new Drink(DrinkType.LATTE);
    latte.addExtra(Extra.EXTRA_SHOT, 2);
    latte.addExtra(Extra.OAT_MILK, 1);
    latte.addExtra(Extra.EXTRA_LARGE_SIZE, 1);

    Drink espresso = new Drink(DrinkType.ESPRESSO);
    espresso.addExtra(Extra.EXTRA_SHOT, 2);

    Drink capp = new Drink(DrinkType.CAPPUCCINO);
    capp.addExtra(Extra.ALMOND_MILK, 1);
    capp.addExtra(Extra.DECAF, 1);
    capp.addExtra(Extra.EXTRA_LARGE_SIZE, 1);

    Order order = new Order("Nkosinathi", calc);
    order.addDrink(latte, 1);
    order.addDrink(espresso, 1);
    order.addDrink(capp, 1);

    // Confirm aliasing is fixed: mutating the original drink should NOT
    // change the order. (Was charging customers extra after payment.)
    latte.addExtra(Extra.EXTRA_SHOT, 5);     // ignored by the order

    System.out.println("Order total: $" + order.total());

    order.pay(new BigDecimal("20.00"));
    order.markReady();

    Receipt r = new Receipt(order, calc);
    System.out.println(r.render());

    // Quick sanity on alt-milk uniqueness still working:
    try {
      Drink bad = new Drink(DrinkType.LATTE);
      bad.addExtra(Extra.OAT_MILK, 1);
      bad.addExtra(Extra.SOY_MILK, 1);   // should fail
      System.out.println("ERROR: alt-milk uniqueness broken");
    } catch (RuntimeException e) {
      System.out.println("Alt-milk uniqueness OK: " + e.getMessage());
    }

    // Confirm cancel works from OPEN, not from PAID
    Order o2 = new Order("Sipho", calc);
    o2.addDrink(espresso, 1);
    o2.cancel();
    System.out.println("Order #" + o2.getOrderNumber() + " status: " + o2.getStatus());

    try {
      Order o3 = new Order("Lerato", calc);
      o3.addDrink(latte, 1);
      o3.pay(new BigDecimal("15.00"));
      o3.cancel();   // illegal — cannot cancel a paid order
      System.out.println("ERROR: cancel from PAID should have failed");
    } catch (RuntimeException e) {
      System.out.println("Cancel-from-PAID rejected: " + e.getMessage());
    }
  }
}
