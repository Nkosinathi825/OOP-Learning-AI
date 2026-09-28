package coffee.shop.ordering.sytem.com.domain.constants;

import java.util.EnumSet;
import java.util.Set;

/**
 * Models the FSM as a graph. The enum owns the *structure* of valid
 * transitions. The act of transitioning (with preconditions and side
 * effects) belongs to Order, which has access to the data those
 * preconditions need (e.g. amountPaid for OPEN -> PAID).
 *
 * No more nextStatus() — that assumed a single linear successor and
 * broke as soon as CANCEL was added as a branch from OPEN.
 */
public enum OrderStatus {

  OPEN, PAID, READY, CANCEL;

  public boolean canTransitionTo(OrderStatus target) {
    return validNextStates().contains(target);
  }

  private Set<OrderStatus> validNextStates() {
    switch (this) {
      case OPEN: return EnumSet.of(PAID, CANCEL);
      case PAID: return EnumSet.of(READY);
      case READY:
      case CANCEL:
      default:   return EnumSet.noneOf(OrderStatus.class);
    }
  }
}
