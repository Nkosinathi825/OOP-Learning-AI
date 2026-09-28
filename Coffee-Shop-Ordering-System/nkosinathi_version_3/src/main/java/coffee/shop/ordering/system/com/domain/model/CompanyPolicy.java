package coffee.shop.ordering.sytem.com.domain.model;

import coffee.shop.ordering.sytem.com.domain.constants.Extra;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

/**
 * Pure validator. Asks "can this be added?" and returns yes/no.
 * Does NOT mutate the caller's data — that's not its data to touch.
 *
 * Modeling alt-milks as an EnumSet (data) instead of an inline OR-chain
 * (code) is a small OCP improvement: adding coconut milk is a one-line
 * data change, not a logic edit.
 *
 * Note: this is still policy-as-a-data-shape rather than policy-as-an-
 * abstraction. If you ever get a policy like "no extra shots before 7am"
 * (time-dependent) or "no decaf for VIPs" (customer-dependent), this
 * static utility class breaks down and you'd want a Policy interface
 * with strategy implementations. Don't add that yet.
 */
public final class CompanyPolicy {

  private static final Set<Extra> ALT_MILKS =
          EnumSet.of(Extra.OAT_MILK, Extra.ALMOND_MILK, Extra.SOY_MILK);

  private CompanyPolicy() { /* utility */ }

  public static boolean canAdd(Map<Extra, Integer> currentExtras,
                               Extra extra,
                               int quantity) {
    if (quantity <= 0) return false;
    if (ALT_MILKS.contains(extra)) {
      if (quantity != 1) return false;
      for (Extra milk : ALT_MILKS) {
        if (currentExtras.containsKey(milk)) return false;
      }
    }
    return true;
  }
}
