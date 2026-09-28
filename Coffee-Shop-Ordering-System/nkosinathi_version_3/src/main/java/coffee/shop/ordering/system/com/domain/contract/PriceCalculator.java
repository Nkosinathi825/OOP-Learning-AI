package coffee.shop.ordering.sytem.com.domain.contract;

import coffee.shop.ordering.sytem.com.domain.model.Drink;
import coffee.shop.ordering.sytem.com.domain.model.Order;

import java.math.BigDecimal;

public interface PriceCalculator {

  BigDecimal drinkPrice(Drink drink);

  BigDecimal orderPrice(Order order);
}
