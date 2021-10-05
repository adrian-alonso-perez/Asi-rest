package es.udc.asi.restexample.model.exception;

public class OperationNotAllowed extends ModelException {

  public OperationNotAllowed(String msg) {
    super("Operation not allowed: " + msg);
  }
}
