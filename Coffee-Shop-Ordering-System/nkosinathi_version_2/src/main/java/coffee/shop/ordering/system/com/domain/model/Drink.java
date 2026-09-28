package coffee.shop.ordering.system.com.domain.model;

import coffee.shop.ordering.system.com.domain.constants.DrinkType;
import coffee.shop.ordering.system.com.domain.constants.Extra;
import coffee.shop.ordering.system.com.domain.exception.IllegalOperationException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Drink  {

  private final DrinkType drinkType;

  private final Map<Extra, Integer> extras;


  public Drink(DrinkType drinkType) {
    this.drinkType = drinkType;
    extras = new HashMap<>();
  }

  public Map<Extra, Integer> getExtras() {
    return Collections.unmodifiableMap(extras);
  }

  public String getDrinkName() {
    return drinkType.toString();
  }

  public boolean addExtra(Extra extra, int quantity) {
    if(CompanyPolicy.addExtra(extras,extra,quantity)){
      return true;
    }
    throw  new IllegalOperationException("You cant add that ");
  }

  public boolean removeExtra(Extra extra, int quantity) {
    if (extras.get(extra) < quantity) {
      throw new IllegalOperationException("You cant remove more than you have ");
    }
    extras.put(extra, extras.get(extra) - quantity);
    return true;
  }


  public int numberOfExtras(){
    return  this.extras.size();
  }

  @Override
  public String toString() {

    return String.format("Name:%s  \n \t \t Extras:\n \t \t \t %s",drinkType.getName(),
            extras.entrySet().stream().collect(StringBuilder::new,
                    (sb ,entry)-> sb.append(String.format("Name:%s  Quantity:%s \n \t \t \t ",
                            entry.getKey(),
                            entry.getValue())),StringBuilder::append).toString());
  }
}
