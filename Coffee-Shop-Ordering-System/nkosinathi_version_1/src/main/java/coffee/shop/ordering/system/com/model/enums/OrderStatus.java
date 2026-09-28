package coffee.shop.ordering.system.com.model.enums;

public enum OrderStatus {

  PLACED("Order placed!!",1),
  IN_PROGRESS("Order in progress!!",2),
  READY("Order ready!!",3),
  COMPLETED("Order completed!!",4);

  private String orderStatus;

  private int ordinal;

  OrderStatus(String orderStatus,int ordinal){
    this.orderStatus = orderStatus;
  }
  public int getOrdinal(){
    return ordinal;
  }
  public String toString(){
    return orderStatus;
  }
}
