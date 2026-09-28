package coffee.shop.ordering.system.com.domain.contract;

import coffee.shop.ordering.system.com.domain.model.Drink;
import coffee.shop.ordering.system.com.domain.model.Order;

import java.math.BigDecimal;

public interface PriceCalculator {

  BigDecimal drinkPrice(Drink drink);

  BigDecimal orderPrice(Order order);

}
