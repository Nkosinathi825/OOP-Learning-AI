package coffee.shop.ordering.system.com.domain.exception;

public class IllegalOperationException extends RuntimeException {

  public IllegalOperationException(String message) {
    super(message);
  }
  public IllegalOperationException(Throwable cause , String message){
    super(message,cause);
  }
}
