package coffee.shop.ordering.system.com.domain.model;

import coffee.shop.ordering.system.com.domain.constants.OrderStatus;
import coffee.shop.ordering.system.com.domain.exception.IllegalOperationException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class Order {

  private long numberOfOrders;

  private final long orderNumber;

  private final Map<Drink, Integer> drinks;

  private final String customerName;

  private OrderStatus status;

  public Order(String name) {
    this.orderNumber = numberOfOrders++;
    this.customerName = name;
    this.drinks = new HashMap<>();
    this.status = OrderStatus.OPEN;
  }



  public long getOrderNumber() {
    return orderNumber;
  }

  public boolean addDrink(Drink drink, int quantity) {
    if (status != OrderStatus.OPEN)
      throw new IllegalOperationException("You cant modify the order anymore");
    if (quantity <= 0)
      throw new IllegalArgumentException("Quantity cant be less than or equal to zero");
    BiFunction<Integer, Integer, Integer> mergeFunction = (a, b) -> a + b;
    drinks.merge(drink, quantity, mergeFunction);
    return true;
  }

  public boolean removeDrink(Drink drink, int quantity) {
    if (status != OrderStatus.OPEN)
      throw new IllegalOperationException("You cant modify the order anymore");
    if (drinks.getOrDefault(drink, 0) <= quantity)
      throw new IllegalArgumentException("Quantity cant be more  than what is available ");
    BiFunction<Integer, Integer, Integer> mergeFunction = (a, b) -> a - b;
    drinks.merge(drink, quantity, mergeFunction);
    return true;
  }
  public boolean advanceOrder(){
    try{
      status = status.nextStatus();
    }catch (IllegalOperationException e){
      throw new IllegalOperationException(e,"Couldn't progress the order ");
    }
    return true;
  }

  public boolean cancelOrder(){
    if (status != OrderStatus.OPEN)
      throw new IllegalOperationException("You cant modify the order anymore");
    status = OrderStatus.CANCEL;
    return true;
  }

  public Map<Drink, Integer> getDrinks() {
    return Collections.unmodifiableMap(drinks);
  }

  public String getCustomerName() {
    return customerName;
  }

  public OrderStatus getStatus() {
    return status;
  }

  @Override
  public String toString() {
    return new StringBuilder("Customer Name :" + this.customerName + "\n")
            .append("Customer Drinks : \n \t")
            .append(getDrinks().entrySet().stream().collect(StringBuilder::new,
                    (a,b)->a.append(b.toString() + "\n"),
                    StringBuilder::append).toString())
            .append(String.valueOf(new StandardCalculator().orderPrice(this))).toString();
  }
}
