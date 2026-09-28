package coffee.shop.ordering.system.com.domain.model;

import coffee.shop.ordering.system.com.domain.constants.Extra;

import java.util.Map;

public class CompanyPolicy {


  //I dont think this is the best way to do it ... I think i need more like a chain of adding
  // extras that can be used cz if a new rule to add an extra comes up the i need to change this
  // method which will violate OCP

  public static boolean addExtra(Map<Extra, Integer> extras, Extra extra, Integer quantity) {
    if (extra == Extra.ALMOND_MILK || extra == Extra.OAT_MILK || extra == Extra.SOY_MILK) {
      if (quantity != 1) return false;
      if (extras.containsKey(Extra.ALMOND_MILK) || extras.containsKey(Extra.OAT_MILK) || extras.containsKey(Extra.SOY_MILK)) {
        return false;
      }
    }
    extras.put(extra, quantity);
    return true;
  }

}
