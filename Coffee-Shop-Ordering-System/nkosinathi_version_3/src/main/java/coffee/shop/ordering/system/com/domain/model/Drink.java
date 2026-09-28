package coffee.shop.ordering.sytem.com.domain.model;

import coffee.shop.ordering.sytem.com.domain.constants.DrinkType;
import coffee.shop.ordering.sytem.com.domain.constants.Extra;
import coffee.shop.ordering.sytem.com.domain.exception.IllegalOperationException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Mutable bag of (DrinkType + extras). Kept mutable to preserve your
 * addExtra/removeExtra API.
 *
 * IMPORTANT FRAGILITY: this class is used as a HashMap key in Order,
 * with content-based equals/hashCode. Mutating a Drink that's currently
 * inside an Order's map would corrupt the map's internal hash buckets.
 * Order protects against this by snapshotting (defensive copy) on add.
 * The copy stored in Order is never mutated by Order. Don't break
 * that contract.
 *
 * The cleaner long-term fix is to make Drink immutable (builder + final
 * fields) — call that out for next time.
 */
public class Drink {

  private final DrinkType drinkType;
  private final Map<Extra, Integer> extras;

  public Drink(DrinkType drinkType) {
    this.drinkType = Objects.requireNonNull(drinkType);
    this.extras = new HashMap<>();
  }

  /** Copy constructor used by Order to snapshot incoming drinks. */
  public Drink(Drink other) {
    Objects.requireNonNull(other);
    this.drinkType = other.drinkType;
    this.extras = new HashMap<>(other.extras);
  }

  public DrinkType getDrinkType() {
    return drinkType;
  }

  public Map<Extra, Integer> getExtras() {
    return Collections.unmodifiableMap(extras);
  }

  public void addExtra(Extra extra, int quantity) {
    Objects.requireNonNull(extra);
    if (!CompanyPolicy.canAdd(extras, extra, quantity)) {
      throw new IllegalOperationException(
              "Cannot add " + quantity + " x " + extra + " to this drink");
    }
    extras.merge(extra, quantity, Integer::sum);
  }

  public void removeExtra(Extra extra, int quantity) {
    Objects.requireNonNull(extra);
    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity must be positive");
    }
    Integer current = extras.get(extra);
    if (current == null || current < quantity) {
      throw new IllegalOperationException(
              "Cannot remove " + quantity + " x " + extra + " — not enough present");
    }
    int remaining = current - quantity;
    if (remaining == 0) {
      extras.remove(extra);   // don't leave zero entries — they break uniqueness checks
    } else {
      extras.put(extra, remaining);
    }
  }

  public int numberOfExtras() {
    return extras.size();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Drink)) return false;
    Drink other = (Drink) o;
    return drinkType == other.drinkType && extras.equals(other.extras);
  }

  @Override
  public int hashCode() {
    return Objects.hash(drinkType, extras);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder(drinkType.getName());
    if (!extras.isEmpty()) {
      sb.append(" (");
      boolean first = true;
      for (Map.Entry<Extra, Integer> e : extras.entrySet()) {
        if (!first) sb.append(", ");
        sb.append(e.getKey().getName());
        if (e.getValue() > 1) sb.append(" x").append(e.getValue());
        first = false;
      }
      sb.append(")");
    }
    return sb.toString();
  }
}
