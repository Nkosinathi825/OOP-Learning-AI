package coffee.shop.ordering.system.com.model.enums;

import java.math.BigDecimal;

public enum DrinkType {

  ESPRESSO("4.50"),
  LATTE("2.00"),
  AMERICANO("6.70"),
  DRIP_COFFEE("10.60");


  private BigDecimal price;
  DrinkType(String price){
   this.price = new BigDecimal(price);
  }

  public BigDecimal getPrice(){
    return price;
  }
}
