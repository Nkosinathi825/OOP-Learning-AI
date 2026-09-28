package coffee.shop.ordering.system.com.model;

import coffee.shop.ordering.system.com.model.enums.OrderStatus;
import coffee.shop.ordering.system.com.model.exception.OrderUpdateException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Order implements Printable{

  private final List<Drink> drinks = new ArrayList<>();

  private OrderStatus orderStatus;

  public Order(Drink... drinks) {
    this.drinks.addAll(List.of(drinks));
  }

  public void addDrinks(Drink drink, int number) {
    if (number == 0) throw new IllegalArgumentException("The number of drinks to be added can be " +
            "equal or less than zero");
    for (int i = 0; i < number; i++) {
      drinks.add(drink);
    }
  }

  public void removeDrinks(Drink drink,int number){
    if (number == 0) throw new IllegalArgumentException("The number of drinks to be added can be " +
            "equal or less than zero");
    for (int i = 0; i < number; i++) {
      drinks.remove(drink);
    }
  }

  public BigDecimal getTotalPrice(){
    BigDecimal price = new BigDecimal("0");
    for(Drink drink : drinks){
      price.add(drink.getTotal());
    }
    return price;
  }

  public void confirmOrder(){
    if(drinks.size() < 1) throw new OrderUpdateException("You can't place an order with no " +
            "drinks");
    orderStatus=OrderStatus.PLACED;
  }

  public OrderStatus getOrderStatus(){
    if(orderStatus == null) throw  new OrderUpdateException("You havent confirmed the order");
    return  orderStatus;
  }

  public boolean cancelOrder(){
    if(orderStatus != OrderStatus.READY && orderStatus != OrderStatus.COMPLETED){
      return true;
    }
    throw new OrderUpdateException("You can't cancel the order now ");
  }
  public void updateOrderStatus(OrderStatus orderStatus){
    if(orderStatus == null) throw  new OrderUpdateException("You havent confirmed the order");
    if (this.orderStatus.getOrdinal() +1 != orderStatus.getOrdinal()){
      throw  new OrderUpdateException("The order of update is wrong ");
    }
  }

  @Override
  public String print() {
    if(orderStatus == null) throw  new OrderUpdateException("You havent confirmed the order");
    return String.format("The ordered drinks are : %s ",drinks);

  }
}
