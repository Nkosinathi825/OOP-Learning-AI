package coffee.shop.ordering.system.com.domain.constants;

import coffee.shop.ordering.system.com.domain.exception.IllegalOperationException;

public enum OrderStatus {

  OPEN,
  CANCEL,
  PAID,
  READY;

  public boolean canTransitionTo(OrderStatus orderStatus) {
    switch (this) {
      case OPEN:
        if (orderStatus == PAID || orderStatus == CANCEL) return true;
      case PAID:
        if (orderStatus == READY) return true;
    }
    return false;
  }
  public OrderStatus nextStatus() throws IllegalOperationException{
    switch (this) {
      case OPEN: return PAID;
      case PAID: return READY;
      case CANCEL: throw  new IllegalOperationException("The order has been cancelled");
      case READY: throw new IllegalOperationException("The order is ready");
      default: throw  new IllegalOperationException("This shouldn't happen");

    }
  }
}
