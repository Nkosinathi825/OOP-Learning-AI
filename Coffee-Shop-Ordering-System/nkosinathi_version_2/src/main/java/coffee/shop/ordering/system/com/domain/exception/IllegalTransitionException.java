package coffee.shop.ordering.system.com.domain.exception;

public class IllegalTransitionException extends RuntimeException {

  public IllegalTransitionException(String message) {
    super(message);
  }
}
